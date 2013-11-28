# Sign server

This app provides only one feature.  
It signs posted data with SHA256withRSA.

I developed this app for using Google OAuth 2.0 for Server to Server Applications in Salesforce.

- https://developers.google.com/accounts/docs/OAuth2ServiceAccount
- https://github.com/shunjikonishi/apex-google-api

## Install

    git clone https://github.com/shunjikonishi/sign-server.git
    cd sign-server
    heroku create
    git push heroku master
    
    # PrivateKey.
    heroku config:set PRIVATE_KEY=<Base64 encoded p12 file>
    # If you don't want to sleep dyno, this app can access next url every 30 minutes.
    heroku config:set POLLING_URL=http://<YOUR APPNAME>.herokuapp.com/

## Usage
HttpRequest
- POST https:&lt;YOUR_HOST>/sign

Parameter
- data: String for sign.

HttpResponse
- Content-type: text/plain
- Body: Signed string
