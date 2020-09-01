# LiveDataBus_Demo

手写liveData，并实现消息总线LiveDataBus

### 原理
[liveData原理总结](https://github.com/yanchunlan/SourceCodeSummary/blob/master/%E6%BA%90%E7%A0%81%E6%80%BB%E7%BB%93/viewModel%E4%B8%8EliveData%E6%BA%90%E7%A0%81%E6%80%BB%E7%BB%93.txt)

### library

| 类 | 描述 |
| ------- | ------- |
| liveData，liveData2 | 手写官方liveData低版本fragment实现生命周期，高版本lifecycle实现生命后期监听   |
| LiveDataBus | 使用自定义LiveDataBus，使用fragment来对生命周期进行管控，并在LiveData里面有缓存处理 |
| LiveDataBus2 | 使用Google原生的MutableLiveData，解决消息通信问题 |
| LiveDataBus3 | 使用Google原生的MutableLiveData，解决消息通信问题，并础上解决了，发送新消息，旧消息影响新消息的bug |

### 总结

实际上liveData就是在观察者模式的基础上加入了生命周期，生命周期的监听策略是低版本是新建fragment控制的，类似于glide的生命周期控制，高版本使用lifecycle的添加观察者监听