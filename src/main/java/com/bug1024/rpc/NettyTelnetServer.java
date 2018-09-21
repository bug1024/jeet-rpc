package com.bug1024.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * @author wangyu
 * @date 2018-09-21
 */
public class NettyTelnetServer {

    private final static int PORT = 8989;
    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * 主入口
     */
    public void open() throws InterruptedException {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTelnetInitializer());

        // 绑定对应的端口号,并启动开始监听端口上的连接
        Channel ch = serverBootstrap.bind(PORT).sync().channel();

        // 等待关闭,同步端口
        ch.closeFuture().sync();
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    /**
     * 初始化配置
     */
    private static class NettyTelnetInitializer extends ChannelInitializer<SocketChannel> {
        private static final StringDecoder DECODER = new StringDecoder();
        private static final StringEncoder ENCODER = new StringEncoder();

        @Override
        protected void initChannel(SocketChannel channel) throws Exception {

            ChannelPipeline pipeline = channel.pipeline();

            // Add the text line codec combination first,
            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

            // 添加编码和解码的类
            pipeline.addLast(DECODER);
            pipeline.addLast(ENCODER);

            // 添加处理业务的类
            pipeline.addLast(new NettyTelnetHandler());

        }
    }

    /**
     * 业务处理
     */
    private static class NettyTelnetHandler extends SimpleChannelInboundHandler<String> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // Send greeting for a new connection.
            ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
            ctx.write("It is " + new Date() + " now.\r\n");
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
            String response;
            boolean close = false;
            if (request.isEmpty()) {
                response = "Please type something.\r\n";
            } else if ("bye".equals(request.toLowerCase())) {
                response = "Have a good day!\r\n";
                close = true;
            } else {
                response = "Did you say '" + request + "'?\r\n";
            }

            ChannelFuture future = ctx.write(response);
            ctx.flush();
            if (close) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }

    }

    public static void main(String[] args) {
        NettyTelnetServer server = new NettyTelnetServer();
        try {
            server.open();
        } catch (InterruptedException e) {
            server.close();
        }
    }

}
