#!/bin/bash

response=$(curl -L -X PUT 'https://quantumclient.org/api/auth/login' \
-H 'Content-Type: application/json' \
-H 'Cookie: auth.strategy=local' \
--data-raw '{
    "username": "'$ACCOUNT_USERNAME'",
    "password": "'$ACCOUNT_PASSWORD'"
}')
echo '$ACCOUNT_USERNAME'
token=$(echo $response | jq --raw-output '.token')

echo $token
