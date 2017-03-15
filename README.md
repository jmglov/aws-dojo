# aws-dojo

AWS coding dojo exercise

## Getting started

Clone the project from GitHub, set your AWS credentials, and start a REPL:

```bash
git clone https://github.com/jmglov/aws-dojo.git
cd aws-dojo
export AWS_ACCESS_KEY_ID=<YOUR_KEY_HERE>
export AWS_SECRET_ACCESS_KEY=<YOUR_SECRET_KEY_HERE>
export AWS_REGION=eu-west-1
lein repl
```

If everything went well, you should see something like this:

```
nREPL server started on port 45315 on host 127.0.0.1 - nrepl://127.0.0.1:45315
REPL-y 0.3.7, nREPL 0.2.12
Clojure 1.9.0-alpha14
Java HotSpot(TM) 64-Bit Server VM 1.8.0_60-b27
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

user=>
```

You win!

You won't need the standalone REPL anymore, so just hit Ctrl-d to exit.

## Nightcode

Text editors arouse passions in software engineers which are amusing but less than optimal for coding dojos where you just want to do something cool cooperatively. For this reason, I recommend using the lovely [Nightcode](https://sekao.net/nightcode/) editor, which is a minimalist, batteries-included Clojure IDE with a REPL and magical formatting powers.

Once you've installed Nightcode and launched it, click the **Import** button at the top and open the directory where you cloned the GitHub repo. Navigate to `src/aws_dojo/core.clj`, and click the **Run with REPL** button in the bottom right pane. Finally, hit **Reload File**, and the prompt in the REPL should change to `aws-dojo.core=>`. Type the following into the REPL and press enter:
```clj
(authenticate!)
```

You should see something like this, in which case your setup is complete and you can now move on to amazing things!
```clj
{:access-key "NOTREALLY123", :secret-key "definitelynot456", :endpoint "eu-west-1"}
```

## Your mission, should you choose to accept it

1. Create an SQS queue
2. Put drawing commands onto it (see below)
3. Read from the queue
4. Transform the drawing commands into a [Hiccup](https://github.com/weavejester/hiccup) data structure containing an SVG image
5. Render your Hiccup data structure into HTML
6. Create an S3 bucket
7. Upload your HTML to S3
8. Get a signed URI for accessing your HTML file
9. View your amazing drawing in your web browser

## Drawing commands

### Create a new drawing

```javascript
{
  "command": "createDrawing",
  "title": "Hello, world!",
  "name": "hello-world",
  "color": "#808080",
  "width": 640,
  "height": 480,
  "shapes": 2
}
```

### Draw a rectangle

```javascript
{
  "command": "drawRectangle",
  "color": "white",
  "point": [20 40],
  "width": 600,
  "height": 400,
}
```

### Draw a circle

```javascript
{
  "command": "drawCircle",
  "color": "red",
  "center": [320 240],
  "radius": 150,
  "z": 1
}
```

## Resources

* SQS
  * [Amazonica API](https://github.com/mcohen01/amazonica#sqs)
  * [Javadoc](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/sqs/AmazonSQSClient.html)
* [Cheshire](https://github.com/dakrone/cheshire): JSON library
* [Hiccup](https://github.com/weavejester/hiccup): HTML library
* [SVG](https://developer.mozilla.org/en-US/docs/Web/SVG)
* S3
  * [Amazonica API](https://github.com/mcohen01/amazonica#sqs)
  * [Javadoc](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html)

## License

Copyright © 2017 Josh Glover <jmglov@gmail.com>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
