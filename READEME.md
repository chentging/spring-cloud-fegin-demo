# 问题描述
这个一个应用 zookeeper和Feign还有Ribbon默认设置的应用例子，但是不知道为什么，不支持PATCH的请求方式。

google 和 baidu 了网上的解决方案：

1. 添加一个 apache-component-client>=4.2+的依赖，原因是PATCH方法是HTTP协议中追加的，原生的JDK 中的URLConnection不支持。***未能成功***

2. 强制在restTemplate中添加HttpComponentsClientHttpRequestFactory实例  ***未能成功***

各种方式都说了，使用zookeeper和Feign在依赖了如下其中之一的依赖后，都会默认调用支持Patch HttpClient的依赖：

1. apache client
```xml
 <dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.2</version>
 </dependency>
```
2. feign client 
```xml
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-httpclient</artifactId>
    <version>9.5.0</version>
</dependency>
```
3. okclient
```xml
<dependency>
  <groupId>com.squareup.okhttp3</groupId>
  <artifactId>okhttp</artifactId>
  <version>3.9.1</version>
</dependency>
```

但是测试了全部的方法。。臣妾做不到啊。

还是throws了一个bug,给上班无聊的我提提神：

```$xslt
2017-12-22 10:49:47.928 DEBUG 13056 --- [           main] o.s.t.c.w.ServletTestExecutionListener   : Resetting RequestContextHolder for test context [DefaultTestContext@4efbca5a testClass = IWalletClientTest, testInstance = com.rporz.demo.rest.client.IWalletClientTest@5773d271, testMethod = test1@IWalletClientTest, testException = feign.RetryableException: Invalid HTTP method: PATCH executing PATCH http://wallet/walletbase/alterBalanceByUserId?userid=24771&balance=8525.19&balanceCode=2525F8F3F72, mergedContextConfiguration = [WebMergedContextConfiguration@1b7cc17c testClass = IWalletClientTest, locations = '{}', classes = '{class com.rporz.demo.DemoApplication}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.context.SpringBootTestContextCustomizer@57175e74, org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@770c2e6b, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@61009542, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@341b80b2], resourceBasePath = 'src/main/webapp', contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]]].

feign.RetryableException: Invalid HTTP method: PATCH executing PATCH http://wallet/walletbase/alterBalanceByUserId?userid=24771&balance=8525.19&balanceCode=2525F8F3F72

	at feign.FeignException.errorExecuting(FeignException.java:67)
	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:104)
	at feign.SynchronousMethodHandler.invoke(SynchronousMethodHandler.java:76)
	at feign.ReflectiveFeign$FeignInvocationHandler.invoke(ReflectiveFeign.java:103)
	at com.sun.proxy.$Proxy92.test(Unknown Source)
	at com.rporz.demo.rest.client.IWalletClientTest.test1(IWalletClientTest.java:19)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks.evaluate(RunBeforeTestMethodCallbacks.java:75)
	at org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks.evaluate(RunAfterTestMethodCallbacks.java:86)
	at org.springframework.test.context.junit4.statements.SpringRepeat.evaluate(SpringRepeat.java:84)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:252)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:94)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:191)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
Caused by: java.net.ProtocolException: Invalid HTTP method: PATCH
	at java.net.HttpURLConnection.setRequestMethod(HttpURLConnection.java:440)
	at sun.net.www.protocol.http.HttpURLConnection.setRequestMethod(HttpURLConnection.java:552)
	at feign.Client$Default.convertAndSend(Client.java:94)
	at feign.Client$Default.execute(Client.java:73)
	at org.springframework.cloud.netflix.feign.ribbon.FeignLoadBalancer.execute(FeignLoadBalancer.java:80)
	at org.springframework.cloud.netflix.feign.ribbon.FeignLoadBalancer.execute(FeignLoadBalancer.java:48)
	at com.netflix.client.AbstractLoadBalancerAwareClient$1.call(AbstractLoadBalancerAwareClient.java:104)
	at com.netflix.loadbalancer.reactive.LoadBalancerCommand$3$1.call(LoadBalancerCommand.java:303)
	at com.netflix.loadbalancer.reactive.LoadBalancerCommand$3$1.call(LoadBalancerCommand.java:287)
	at rx.internal.util.ScalarSynchronousObservable$3.call(ScalarSynchronousObservable.java:231)
	at rx.internal.util.ScalarSynchronousObservable$3.call(ScalarSynchronousObservable.java:228)
	at rx.Observable.unsafeSubscribe(Observable.java:10151)
	at rx.internal.operators.OnSubscribeConcatMap$ConcatMapSubscriber.drain(OnSubscribeConcatMap.java:286)
	at rx.internal.operators.OnSubscribeConcatMap$ConcatMapSubscriber.onNext(OnSubscribeConcatMap.java:144)
	at com.netflix.loadbalancer.reactive.LoadBalancerCommand$1.call(LoadBalancerCommand.java:185)
	at com.netflix.loadbalancer.reactive.LoadBalancerCommand$1.call(LoadBalancerCommand.java:180)
	at rx.Observable.unsafeSubscribe(Observable.java:10151)
	at rx.internal.operators.OnSubscribeConcatMap.call(OnSubscribeConcatMap.java:94)
	at rx.internal.operators.OnSubscribeConcatMap.call(OnSubscribeConcatMap.java:42)
	at rx.Observable.unsafeSubscribe(Observable.java:10151)
	at rx.internal.operators.OperatorRetryWithPredicate$SourceSubscriber$1.call(OperatorRetryWithPredicate.java:127)
	at rx.internal.schedulers.TrampolineScheduler$InnerCurrentThreadScheduler.enqueue(TrampolineScheduler.java:73)
	at rx.internal.schedulers.TrampolineScheduler$InnerCurrentThreadScheduler.schedule(TrampolineScheduler.java:52)
	at rx.internal.operators.OperatorRetryWithPredicate$SourceSubscriber.onNext(OperatorRetryWithPredicate.java:79)
	at rx.internal.operators.OperatorRetryWithPredicate$SourceSubscriber.onNext(OperatorRetryWithPredicate.java:45)
	at rx.internal.util.ScalarSynchronousObservable$WeakSingleProducer.request(ScalarSynchronousObservable.java:276)
	at rx.Subscriber.setProducer(Subscriber.java:209)
	at rx.internal.util.ScalarSynchronousObservable$JustOnSubscribe.call(ScalarSynchronousObservable.java:138)
	at rx.internal.util.ScalarSynchronousObservable$JustOnSubscribe.call(ScalarSynchronousObservable.java:129)
	at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:48)
	at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:30)
	at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:48)
	at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:30)
	at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:48)
	at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:30)
	at rx.Observable.subscribe(Observable.java:10247)
	at rx.Observable.subscribe(Observable.java:10214)
	at rx.observables.BlockingObservable.blockForSingle(BlockingObservable.java:444)
	at rx.observables.BlockingObservable.single(BlockingObservable.java:341)
	at com.netflix.client.AbstractLoadBalancerAwareClient.executeWithLoadBalancer(AbstractLoadBalancerAwareClient.java:112)
	at org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient.execute(LoadBalancerFeignClient.java:63)
	at org.springframework.cloud.zookeeper.discovery.dependency.DependencyFeignClientAutoConfiguration$1.execute(DependencyFeignClientAutoConfiguration.java:83)
	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:97)
	... 32 more


```

只了另想办法，大路不通我就走小路。找原开发者帮忙[#2550](https://github.com/spring-cloud/spring-cloud-netflix/issues/2550)

提出几个有意见性的问题：

1. 版本不支持了。升级 `spring-boot 1.5.9` 和 `spring-cloud Dalston.SR4` ，但是面对问题卒。。
2. 移除了zookeeper ，纯使用Ribbon的设置`xx.ribbon.listOfServers`,我草，我想骂人。。成功了。。但是，我就是喜欢zk啊。。

在原开发者建议2的基础上，我想不会是zookeeper的autoConfig生成了一些的Bean。。所有看了一下源代码。。。但是源代码它认识我，我不认为它啊。。感觉是我要卒。硬杠着吐血的冲动，看了下面几个类的代码：

1. `RibbonAutoConfiguration`
2. `FeignRibbonClientAutoConfiguration`
3. `DependencyFeignClientAutoConfiguration`

看完之后，有了一个设置`spring.cloud.zookeeper.dependency.headers.enabled=false`,成功了
但是在类 DependencyFeignClientAutoConfiguration 中详细点看代码。就生成了一个LoadBalancerFeignClient的Bean，配置的前提的，在1，2类的前提下，为什么会没有生成一个依赖呢？

在怀着还有更好的方法的前提下，我卒。

享年25岁。。。。

