#!/bin/bash

# Delete private key if it exists
#
PRIVATE=private_key_rsa_4096_pkcs8.pem
if [ -f "$PRIVATE" ]; then
    read -p "Delete existing $PRIVATE file (y/n): " yn
    if [ "$yn" == "y" ]; then
        rm -f $PRIVATE
    else
        echo EXIT
        exit 0
    fi
fi

# Generate private key with pkcs8 encoding
#
openssl genpkey -out $PRIVATE -algorithm RSA -pkeyopt rsa_keygen_bits:4096

# Delete public key if it exists
#
PUBLIC=public_key_rsa_4096_pkcs8.pem
if [ -f "$PUBLIC" ]; then
    read -p "Delete existing $PUBLIC file (y/n): " yn
    if [ "$yn" == "y" ]; then
        rm -f $PUBLIC
    else
        echo EXIT
        exit 0
    fi
fi

# Export public key in pkcs8 format
openssl rsa -pubout -outform pem -in $PRIVATE -out $PUBLIC