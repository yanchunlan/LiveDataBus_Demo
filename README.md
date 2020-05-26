# LiveDataBus_Demo

手写消息总线LiveDataBus

### LiveDataBus：

使用自定义LiveDataBus，使用fragment来对生命周期进行管控，并在LiveData里面有缓存处理

### LiveDataBus2：

使用Google原生的MutableLiveData，解决消息通信问题

### LiveDataBus3：

使用Google原生的MutableLiveData，解决消息通信问题，并础上解决了，发送新消息，旧消息影响新消息的bug

### 总结

实际上liveData就是在观察者模式的基础上加入了生命周期，生命周期的监听策略是新建fragment控制的，类似于glide的生命周期控制