#!/bin/bash

eval "$(ssh-agent -s)"
chmod 600 ~/.ssh/id_rsa
ssh-add ~/.ssh/id_rsa


ssh root@$DO_SERVER <<EOF
  docker stop lttry
  docker rm lttry
  docker rmi ignatev/lottery
  docker pull ignatev/lottery
  docker run -d -p 8080:8080 --name lttry ignatev/lottery
EOF
