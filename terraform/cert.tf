resource "aws_acm_certificate" "default" {
  // We want a wildcard cert so we can host subdomains later.
  domain_name       = "*.${var.root_domain_name}"
  validation_method = "DNS"

  // We also want the cert to be valid for the root domain even though we'll be
  // redirecting to the www. domain immediately.
  subject_alternative_names = ["${var.root_domain_name}"]
}

// importing this zone. it was created by route53 when the domain was registered
data "aws_route53_zone" "zone" {
  name = "${var.root_domain_name}"
}

resource "aws_route53_record" "www" {
  zone_id = "${data.aws_route53_zone.zone.zone_id}"
  name    = "${var.www_domain_name}"
  type    = "A"
  ttl     = 300


  // we will set up cloudfront later, for now just point to eip
//  alias = {
//    name                   = "${aws_cloudfront_distribution.www_distribution.domain_name}"
//    zone_id                = "${aws_cloudfront_distribution.www_distribution.hosted_zone_id}"
//    evaluate_target_health = false
//  }

  records = ["${aws_eip.ip.public_ip}"]
}

resource "aws_route53_record" "validation" {
  name    = "${aws_acm_certificate.default.domain_validation_options.0.resource_record_name}"
  type    = "${aws_acm_certificate.default.domain_validation_options.0.resource_record_type}"
  zone_id = "${data.aws_route53_zone.zone.zone_id}"
  records = ["${aws_acm_certificate.default.domain_validation_options.0.resource_record_value}"]
  ttl     = "60"
}

resource "aws_acm_certificate_validation" "default" {
  certificate_arn = "${aws_acm_certificate.default.arn}"
  validation_record_fqdns = [
    "${aws_route53_record.validation.fqdn}",
  ]

  # Add a timeout so a dev isn't waiting for the default 45 minute timeout
  timeouts {
    create = "5m"
  }
}