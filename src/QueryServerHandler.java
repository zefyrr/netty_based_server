import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

public class QueryServerHandler extends SimpleChannelInboundHandler<String> {

  private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }


  @Override
  protected void channelRead0(final ChannelHandlerContext ctx, final String query) throws Exception {

    final long startTime_google = System.currentTimeMillis();
    asyncHttpClient.prepareGet("https://www.google.com/?q=" + query).execute(
        new AsyncCompletionHandler<Integer>() {
          @Override
          public Integer onCompleted(Response response) throws Exception {
            Long timeTaken = System.currentTimeMillis() - startTime_google;
            ctx.writeAndFlush(String.format("Google query - %s, time taken - %d ms\n", query,
                timeTaken));
            return null;
          }
        });

    final long startTime_google_fr = System.currentTimeMillis();
    asyncHttpClient.prepareGet("https://www.google.fr/#q=" + query).execute(
        new AsyncCompletionHandler<Integer>() {
          @Override
          public Integer onCompleted(Response response) throws Exception {
            Long timeTaken = System.currentTimeMillis() - startTime_google_fr;
            ctx.writeAndFlush(String.format("Google France query - %s, time taken - %d ms\n",
                query, timeTaken));
            return null;
          }
        });


    final long startTime_bing = System.currentTimeMillis();
    asyncHttpClient.prepareGet("https://www.bing.com/search?q=" + query).execute(
        new AsyncCompletionHandler<Integer>() {
          @Override
          public Integer onCompleted(Response response) throws Exception {
            Long timeTaken = System.currentTimeMillis() - startTime_bing;
            ctx.writeAndFlush(String.format("Bing query - %s, time taken - %d ms\n", query,
                timeTaken));
            return null;
          }
        });


    final long startTime_baidu = System.currentTimeMillis();
    asyncHttpClient.prepareGet("https://www.baidu.com/s?wd=" + query).execute(
        new AsyncCompletionHandler<Integer>() {
          @Override
          public Integer onCompleted(Response response) throws Exception {
            Long timeTaken = System.currentTimeMillis() - startTime_baidu;
            ctx.writeAndFlush(String.format("Baidu query - %s, time taken - %d ms\n", query,
                timeTaken));
            return null;
          }
        });

  }
}
