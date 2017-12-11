##FishHttp
>A SUPER-LIGHT HTTP REQUEST UTIL

PS: Chinese-Readme in ReadMe-CH.md file

YOU CAN SEE DEMO IN FISHHTTP--DEMO--GET

>This http request use Annotation & Builder mode to configure request params, so is more easily to use

###For android studio developers
After:
1. Download or git clone this module in your project dir:
2. Open __settings.gradle__:
```settings.gradle
    include ':app',":FishHttp"

```

3. Open __build.gradle of the Module__:
```build.gradle
    dependencies{
        //...
        compile project(':FishHttp')
    }

```
4. Add uses-permission :
```AndroidManifest
    android.permission.INTERNET
    android.permission.READ_PHONE_STATE
    android.permission.ACCESS_NETWORK_STATE
    android.permission.ACCESS_WIFI_STATE
```
 
5. In __build.gradle of the Project__: 
```build.gradle
    buildscript{
        dependencies{
            //...
            //support lambda expression
            classpath 'me.tatarka:gradle-retrolambda:3.2.5'
        }
    }

```

###Start use it
1. Create a RequestHelper entity~ like this:

```
        
        @NetMethod(RequestHelper.Method.GET)
        
        //URl
        @NetUrl(Constant.JOKE_API_GET)
        
        //Response Entity Class
        @Result(JokeResponseEntity.class)
        
        RequestHelper<JokeResponseEntity> getHelper;
        
        
         getHelper
         .HeaderParam(Constant.JOKE_API_KEY, Constant.BAIDU_API_KEY)
         .UrlParam("path", "1", true)
         .Success(new Done<JokeResponseEntity>() {
                @Override
                public void run(JokeResponseEntity responseEntity) {
                //todo handle result
                )
         .Failed((msg) -> Toast.makeText(JokeActivity.this, (String)msg, Toast.LENGTH_SHORT).show())
         .get(this, new Handler(Looper.getMainLooper()));

```

####To make this annotation work, you mush call NetInject.inject() first~~~


2. if use post request and need add body params, use this method:
```

        @NetMethod(RequestHelper.Method.POST)
        
        @NetUrl(URL)
        
        @Result(ResponseEntity.class)
        
        private RequestHelper mHelper;
        private ResponseEntity entity;
        
        mHelper
        .PostParam(new PostParam("LALALA  I AM COMING~"))
        .Success((result) -> {
                    //todo handle resukt
                })
        .Failed((msg)-> Toast.makeText(PostActivity.this, (String)msg, Toast.LENGTH_SHORT).show())
        .post(this, new Handler(Looper.getMainLooper()));

```

Glad to receieve any suggest from you ; __Email: pekphet@126.com__

- __UPDATE 0.2__ REMOVE BaseEntity Type limit of Request result~

- __UPDATE 0.3__
fix a bug : url params adder bug~ 
support add header properties;
add url param appender:
```
        UrlParam(REQEST_PARAMS, Boolean isFirst)
        
        UrlParam(Key,Value,Boolean isFirst)
        
        //default: UrlParam(REQEST_PARAMS, true),used for single param bean;
        UrlParam(REQEST_PARAMS) 
        
        //default: UrlParam(Key,Value,false), used for param append quickly~
        UrlParam(Key,Value)
```

- __UPDATE 0.4__
1. Change POST request and FIX BUGS in POST METHOD!
2. Support for 'application/x-www-form-urlencoded' and 'application/json' in 2 different API
The API of add body-parameters:
3. application/x-www-form-urlencoded use: PostParam(Serializable obj) returns RequestHelper 
application/json use : PostJson(Object obj) returns RequestHelper
4. My POST METHOD server may be not open usually~ so you can use post server of your corp.
And Come in My QQ GROUP for discussion~~

- __UPDATE 0.5__
1. Fix BUGS in NetInject (NP Exception QwQ)
2. Post parameter adder can be used with key-value (default:NOT first)
3. Add TypeToken setter while using List Generic type, U can try TypeToken instead of using Result (class)
4. Add sync-post/get request method to keep sync~

- __UPDATE 0.51__
1. Fix bug: A bug of OKHttp: post chinese word will be crashed.

- __UPDATE 1.0__
1. Add upload single file/image file support.
2. Using postSingle* Method, PostParam will be not enable.

- __UPDATE 1.01__
1. FIX ONE BUG, using UrlParam will add multiple when isFirst=true

- __UPDATE 1.1__
1. No need EDIT GRADLE FILES, SUPPORT HTTPS, TLS 2-WAY-AUTH SUPPORTS, REFACTOR CODES