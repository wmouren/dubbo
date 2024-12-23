/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.remoting.exchange;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.remoting.Constants;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchanger;

/**
 * Exchanger. (SPI, Singleton, ThreadSafe)
 * <p>
 * <a href="http://en.wikipedia.org/wiki/Message_Exchange_Pattern">Message Exchange Pattern</a>
 * <a href="http://en.wikipedia.org/wiki/Request-response">Request-Response</a>
 */
// 交换机 SPI 接口 用于消息交换 默认实现是 HeaderExchanger 用于消息头交换

/**
 *  消息交换模式MEP
 *
 *  在软件架构中，消息传递模式是一种架构模式，它描述应用程序的两个不同部分或不同系统如何相互连接和通信。
 *  消息传递的概念有很多方面，可分为以下几类：硬件设备消息传递（电信、计算机网络、物联网等）和软件数据交换（不同的数据交换格式和此类数据交换的软件功能） 。
 *  尽管上下文存在差异，但这两个类别都表现出数据交换的共同特征
 *
 *  在电信中，消息交换模式 (MEP) 描述了通信协议（protocol）建立或使用通信通道（ channel）所需的消息模式。通信协议是用于表示所有通信方都同意（或能够处理）的消息的格式。
 *  通信通道是使消息能够在通信双方之间“传播”的基础设施（channel）。消息交换模式描述了通信过程中各方之间的消息流，
 *  有两种主要的消息交换模式—— 请求-响应模式（Request-Response） 和  单向模式。
 *
 *  衍生知识：在 Rsocket 中有以下几种消息交换模式：
 *      1. Request-Response  (stream of 1)
 *      2. Fire-and-Forget  (no response)
 *      3. Request-Stream (finite/infinite stream of many) 类似 服务器发送事件（Server-Sent Events，SSE）模式
 *      4. Channel (bi-directional streams)
 *
 *
 *
 * 在这里ExchangeClient 和 ExchangeServer 是对应的，ExchangeClient 是客户端，ExchangeServer 是服务端
 * 它们直接通过 ExchangeChannel 进行通信 ExchangeChannel 是一个通道，用于客户端和服务端之间的通信
 * 一个 ExchangeChannel 对应一个 ExchangeHandler 用于处理消息的发送和接收
 *
 *
 * ExchangeChannel 和 ExchangeHandler 通常一起工作。当 ExchangeChannel 接收到一个请求时，它会将请求传递给 ExchangeHandler 进行处理，
 * 然后将 ExchangeHandler 的响应返回给请求者。这样，ExchangeChannel 和 ExchangeHandler 一起实现了请求响应模式的消息交换。
 * 在 ExchangeChannel 中，有一个 getExchangeHandler 方法，这个方法返回的就是与该通道关联的 ExchangeHandler。
 * 当 ExchangeChannel 收到一个请求时，它会将这个请求交给 ExchangeHandler 来处理，并等待 ExchangeHandler 返回一个响应。
 *
 *
 * Request-Response 模式,对应 client 和 server 两端之间得请求和响应 对应得对象是 Request 和 Response对象
 *
 * 这是 dubbo 远程通信中的一种模式，客户端向服务端发送一个请求，服务端接收到请求后进行处理，然后返回一个响应给客户端。
 *
 * ExchangeServer、ExchangeClient、ExchangeChannel、ExchangeHandler、Request、Response
 * 这几个类都是 dubbo 中用于实现 Request-Response 模式的规范接口。 定义了整个通信流程的抽象骨架
 */
@SPI(HeaderExchanger.NAME)
public interface Exchanger {

    /**
     * bind.
     *
     * @param url
     * @param handler
     * @return message server
     */
    @Adaptive({Constants.EXCHANGER_KEY})
    ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException;

    /**
     * connect.
     *
     * @param url
     * @param handler
     * @return message channel
     */
    @Adaptive({Constants.EXCHANGER_KEY})
    ExchangeClient connect(URL url, ExchangeHandler handler) throws RemotingException;

}
