#!/bin/bash

# Prompt user for a value
echo -n "Value: "
read -s val1
echo
echo -n "Enter again: "
read -s val2
echo

if [ "$val1" == "$val2" ]; then
        b64=`printf "$val2" | openssl rsautl -encrypt -inkey public_key_rsa_4096_pkcs8.pem -pubin | openssl enc -A -base64`
        echo
        echo "COPY AND PASTE"
        echo
        echo rsa{$b64}
        echo
    else
        echo Values are not equal
        echo EXIT
        exit 0
    fi

echo EXIT
