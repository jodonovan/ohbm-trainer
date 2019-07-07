# Define SSH key pair for our instances
resource "aws_key_pair" "default" {
  key_name = "ohbm-trainer"
  public_key = "${file("${var.key_path}")}"
}

# Define webserver inside the public subnet
resource "aws_instance" "wb" {
  ami  = "${var.ami}"
  instance_type = "t3.nano"
  key_name = "${aws_key_pair.default.id}"
  subnet_id = "${aws_subnet.public-subnet.id}"
  vpc_security_group_ids = ["${aws_security_group.sgweb.id}"]
  associate_public_ip_address = true
  source_dest_check = false
#  user_data = "${file("install.sh")}"

  tags = {
    Name = "webserver"
  }
}

resource "aws_eip" "ip" {
  instance = aws_instance.wb.id
  vpc = true

  provisioner "remote-exec" {
    inline = [
      "sudo yum -y install java-1.8.0-openjdk-1.8.0.151-1.b12.amzn2"
    ]
  }

  provisioner "file" {
    source      = "../target/ohbmtrainer-0.0.1-SNAPSHOT.jar"
    destination = "~/ohbmtrainer-0.0.1-SNAPSHOT.jar"
  }

  provisioner "remote-exec" {
    inline = [
      "mkdir -p ~/images/bhumi/pending",
      "mkdir -p ~/images/awakening/pending",
    ]
  }

  provisioner "file" {
    source = "../src/main/resources/static/images/bhumi/"
    destination = "~/images/bhumi"
  }

  provisioner "file" {
    source = "../src/main/resources/static/images/bhumi/pending/"
    destination = "~/images/bhumi/pending"
  }

  provisioner "file" {
    source = "../src/main/resources/static/images/awakening/"
    destination = "~/images/awakening"
  }

  provisioner "file" {
    source = "../src/main/resources/static/images/awakening/pending/"
    destination = "~/images/awakening/pending"
  }

  provisioner "remote-exec" {
    inline = [
      "sudo java -jar -Xmx256m -Xss256k -Dspring.profiles.active=prod -Dimage.directory.bhumi=~/images/bhumi -Dimage.directory.awakening=~/images/awakening ohbmtrainer-0.0.1-SNAPSHOT.jar --server.port=80 &"
    ]
  }

  connection {
    type = "ssh"
    user = "ec2-user"
    host = "${aws_eip.ip.public_ip}"
    password = ""
    private_key = "${file("${var.key_path_private}")}"
  }
}

output "webserver_ip" {
  value = aws_instance.wb.public_ip
}

output "eip_ip" {
  value = aws_eip.ip
}
