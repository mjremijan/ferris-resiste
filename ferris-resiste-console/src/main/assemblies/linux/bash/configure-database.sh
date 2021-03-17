#!/bin/bash

# Generate private key with pkcs8 encoding
rm -f private_key_rsa_4096_pkcs8.pem
openssl genpkey -out private_key_rsa_4096_pkcs8.pem -algorithm RSA -pkeyopt rsa_keygen_bits:4096

# Export public key in pkcs8 format
rm -f private_key_rsa_4096_pkcs8.pem
openssl rsa -pubout -outform pem -in private_key_rsa_4096_pkcs8.pem -out public_key_rsa_4096_pkcs8.pem