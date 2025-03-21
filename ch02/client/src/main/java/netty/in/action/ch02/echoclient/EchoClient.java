package netty.in.action.ch02.echoclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EchoClient {
  private final String host;
  private final int port;

  public void start() throws InterruptedException {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
          .channel(NioServerSocketChannel.class)
          .remoteAddress(new InetSocketAddress(host, port))
          .handler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline().addLast(new EchoClientHandler());
                }
              });
      ChannelFuture f = b.connect().sync();
      f.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    if (args.length != 2) {
      System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
      return;
    }

    final String host = args[0];
    final int port = Integer.parseInt(args[1]);
    new EchoClient(host, port).start();
  }
}
