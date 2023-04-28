# browser-ms
browser_ms


This is The Source Lambda's microservice charged of browser functionalities.

If you want to test them, you should first download following docker images:

https://hub.docker.com/repository/docker/justdangel/sourcelambda_browser_ms/general
https://hub.docker.com/repository/docker/justdangel/sourcelambda_browser_db/general

Once you have downloaded'em, you must follow next steps:

1. run database container with

      docker run -p 6379:6379 justdangel/sourcelambda_browser_db

2. then, run microservice container with

      docker run -p 8085:8085 -e REDIS_BROWSER_HOST=X -e REDIS_BROWSER_PORT=Y -e RABBITMQ_HOST=Z -e RABBITMQ_QUEUE_NAME=W justdangel/sourcelambda_browser_ms
      
      where,
      
      X = database host address (previous container)
      Y = port where database host is runing
      Z = host where rabbitmq is runnig, as this service keeps listening to a queue (for more info, please check: https://github.com/SourceLambda/sourcelambda_mq)
      W = The queue name which the service will be attending (usually is "indexer")
