#!/bin/bash

while getopts t:u:d: option
do
case "${option}"
in
t) TYPE=${OPTARG};;
u) UPLOAD=${OPTARG};;
d) DISCORD=${OPTARG};;
esac
done

case $TYPE in
  "success" )
    EMBED_COLOR=3066993
    STATUS_MESSAGE="Passed"
    AVATAR="https://travis-ci.com/images/logos/TravisCI-Mascot-blue.png"
    ;;

  "failure" )
    EMBED_COLOR=15158332
    STATUS_MESSAGE="Failed"
    AVATAR="https://travis-ci.com/images/logos/TravisCI-Mascot-red.png"
    ;;

  * )
    EMBED_COLOR=0
    STATUS_MESSAGE="Status Unknown"
    AVATAR="https://travis-ci.com/images/logos/TravisCI-Mascot-1.png"
    ;;
esac

shift
VRS=$(cat ../gradle.properties| grep ../mod_version | sed 's/.*=//')
json=$(curl -H 'Content-Type: multipart/form-data' -X POST -F "file=@../build/libs/Qubit-$VRS.jar" "$UPLOAD" )
JARLINK=$(echo $json | sed 's/\\\\\//\//g' | sed 's/[{}]//g' | awk -v k="text" '{n=split($0,a,","); for (i=1; i<=n; i++) print a[i]}' | sed 's/\"\:\"/\|/g' | sed 's/[\,]/ /g' | sed 's/\"//g' | grep -w 'url' | sed 's/.* //' )

if [  $TYPE="success" ]; then
    export TOKEN="$(./login.sh)"
    (curl -L -X PUT 'https://quantumclient.org/api/projects/qubit/link' \
-H 'Authorization: Bearer '$TOKEN'' \
-H 'Content-Type: application/json' \
-H 'Cookie: auth.strategy=local' \
--data-raw '{
    "name": "Qubit",
    "link": "'$JARLINK'"
}')
fi


AUTHOR_NAME="$(git log -1 "$TRAVIS_COMMIT" --pretty="%aN")"
COMMITTER_NAME="$(git log -1 "$TRAVIS_COMMIT" --pretty="%cN")"
COMMIT_SUBJECT="$(git log -1 "$TRAVIS_COMMIT" --pretty="%s")"
COMMIT_MESSAGE="$(git log -1 "$TRAVIS_COMMIT" --pretty="%b")" | sed -E ':a;N;$!ba;s/\r{0,1}\n/\\n/g'

if [ ${#COMMIT_SUBJECT} -gt 256 ]; then
  COMMIT_SUBJECT="$(echo "$COMMIT_SUBJECT" | cut -c 1-253)"
  COMMIT_SUBJECT+="..."
fi

if [ -n $COMMIT_MESSAGE ] && [ ${#COMMIT_MESSAGE} -gt 1900 ]; then
  COMMIT_MESSAGE="$(echo "$COMMIT_MESSAGE" | cut -c 1-1900)"
  COMMIT_MESSAGE+="..."
fi

if [ "$AUTHOR_NAME" == "$COMMITTER_NAME" ]; then
  CREDITS="$AUTHOR_NAME authored & committed"
else
  CREDITS="$AUTHOR_NAME authored & $COMMITTER_NAME committed"
fi

if [[ $TRAVIS_PULL_REQUEST != false ]]; then
  URL="https://github.com/$TRAVIS_REPO_SLUG/pull/$TRAVIS_PULL_REQUEST"
else
  URL="https://github.com/$TRAVIS_REPO_SLUG/"
fi

TIMESTAMP=$(date -u +%FT%TZ)
WEBHOOK_DATA='{
  "username": "Qubit Builds",
  "avatar_url": "https://cdn.discordapp.com/avatars/798405652111818792/11cc2ae56888f9a4ea2dae67ed1fe756.png",
  "embeds": [ {
    "color": '$EMBED_COLOR',
    "title": "Qubit '"$VRS"' Build #'"$TRAVIS_BUILD_NUMBER"' '"$STATUS_MESSAGE"'" ,
    "url": "'"$URL"'",
    "description": "[**DOWNLOAD**]('$JARLINK')",
    "fields": [
      {
        "name": "Commit: '$COMMIT_SUBJECT'",
        "value": "'"${COMMIT_MESSAGE//$'\n'/ }"\\n\\n"$CREDITS"'",
        "inline": false
      },
      {
        "name": "\u200B",
        "value": "['"$TRAVIS_COMMIT"'](https://github.com/'$TRAVIS_REPO_SLUG'/commit/'$TRAVIS_COMMIT')",
        "inline": false
      }
    ],
    "timestamp": "'$TIMESTAMP'"
  } ]
}'
shift
echo -e "[Webhook]: Sending webhook to Discord...\\n";

(curl --fail -H Content-Type:application/json -H X -d "${WEBHOOK_DATA//	/ }" "$DISCORD" \
&& echo -e "\\n[Webhook]: Successfully sent the webhook.") || echo -e "\\n[Webhook]: Unable to send webhook."
