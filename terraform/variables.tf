variable "aws_region" {
  description = "Region for the VPC"
  default = "us-east-1"
}

variable "vpc_cidr" {
  description = "CIDR for the VPC"
  default = "10.0.0.0/16"
}

variable "public_subnet_cidr" {
  description = "CIDR for the public subnet"
  default = "10.0.1.0/24"
}

variable "private_subnet_cidr" {
  description = "CIDR for the private subnet"
  default = "10.0.2.0/24"
}

variable "ami" {
  description = "Amazon Linux AMI"
  default = "ami-0c6b1d09930fac512"
}

variable "key_path" {
  description = "SSH Public Key path"
  default = "~/.ssh/ohbm-trainer.pub"
}

variable "key_path_private" {
  description = "SSH Public Key path"
  default = "~/.ssh/ohbm-trainer.pem"
}

// Create a variable for our domain name because we'll be using it a lot.
variable "www_domain_name" {
  default = "www.ohbm-app.org"
}

// We'll also need the root domain (also known as zone apex or naked domain).
variable "root_domain_name" {
  default = "ohbm-app.org"
}