provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "example" {
  ami = "ami-0c6b1d09930fac512"
  instance_type = "t2.micro"
  subnet_id = "subnet-89f81bb7"
  key_name = "ohbm"

  provisioner "local-exec" {
    command = "echo ${aws_instance.example.public_ip} > ip_address.txt"
  }
}

resource "aws_eip" "ip" {
  instance = aws_instance.example.id
  vpc = true

  provisioner "remote-exec" {
    inline = [
      "date > /tmp/theDate",
      "sudo yum -y install java-1.8.0-openjdk-1.8.0.151-1.b12.amzn2"
    ]
  }
  connection {
    type = "ssh"
    user = "ec2-user"
    host = "${aws_eip.ip.public_ip}"
    password = ""
    private_key = "${file("~/.ssh/ohbm.pem")}"
  }
}


output "ip" {
  value = aws_eip.ip.public_ip
}
