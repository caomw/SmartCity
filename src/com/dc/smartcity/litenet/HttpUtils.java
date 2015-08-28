package com.dc.smartcity.litenet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.dc.smartcity.util.ULog;

import android.util.Log;

public class HttpUtils {
    public static final int METHOD_GET  = 1;
    public static final int METHOD_POST = 2;
    private static final Pattern PATTERN_META_REDIR = Pattern.compile("<meta\\s+http-equiv\\s*=\\s*\"\\s*refresh\\s*\"\\s*content\\s*=\\s*\".+?url=(.+?)\\s*\"", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_META_ENCODING = Pattern.compile("<meta.+?\\s*?;\\s*?charset=(.+?)(\\\"|')", Pattern.CASE_INSENSITIVE);

    org.apache.http.client.HttpClient httpClient;
    private HttpResponseData response;
    private static boolean LogEnable = false;
    private static String LogPathName = "/http.log";

    public HttpUtils(HttpParams params) {
        initHttpClient(params);
    }
    
    public HttpUtils(String ua,int connTimeout,int soTimeout) {
        HttpParams params = new org.apache.http.params.BasicHttpParams();
        if(ua!=null) org.apache.http.params.HttpProtocolParams.setUserAgent(params, ua);
        if(connTimeout!=-1) org.apache.http.params.HttpConnectionParams.setConnectionTimeout(params, connTimeout);
        if(soTimeout!=-1) org.apache.http.params.HttpConnectionParams.setSoTimeout(params, soTimeout);
        HttpClientParams.setRedirecting(params, true);
        
        initHttpClient(params);
    }
    
    private void initHttpClient(HttpParams params) {
        org.apache.http.conn.scheme.SchemeRegistry schemeRegistry = new org.apache.http.conn.scheme.SchemeRegistry();
        schemeRegistry.register(new org.apache.http.conn.scheme.Scheme("http", org.apache.http.conn.scheme.PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new org.apache.http.conn.scheme.Scheme("https", new EasySSLSocketFactory(), 443));
        org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager cm = new org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager(params, schemeRegistry);

        httpClient = new org.apache.http.impl.client.DefaultHttpClient(cm, params);
       
    }
    
    public static void EnableLogFile(boolean enable) {
        LogEnable = enable;
    }
    
    public static void SetLogFile(String pathname) {
        if(!pathname.startsWith("/")) {
            LogPathName="/"+pathname;
        } else {
            LogPathName = pathname;
        }
    }
/*
    public void doHttpGetNoRet(String url,boolean recuive) {
        HttpResponseData r = doHttpGet(url, recuive);
        r.close();
    }

    public void doHttpPostNoRet(String url,HttpEntity httpEntity,boolean recuive) {
        HttpResponseData r = doHttpPost(url, httpEntity, recuive);
        r.close();
    }

    public void doHttpRequestNoRet(int method, String url, java.util.List<Header> headers, HttpEntity httpEntity,boolean recuive) {
        HttpResponseData r = doHttpRequest(method, url, headers, httpEntity, recuive);
        r.close();
    }
*/
    public static HttpResponseData DoHttpGet(String url,boolean recuive) {
        HttpUtils http = new HttpUtils(null,10*1000,10*1000);
        return http.doHttpRequest(METHOD_GET, url, null, null, recuive);
    }
    
    public static HttpResponseData DoHttpGet(java.net.URL url,boolean recuive) {
        HttpUtils http = new HttpUtils(null,10*1000,10*1000);
        return http.doHttpRequest(METHOD_GET, url.toString(), null, null, recuive);
    }
    
    public HttpResponseData doHttpGet(String url,boolean recuive) {
        return doHttpRequest(METHOD_GET, url, null, null, recuive);
    }
    
    public HttpResponseData doHttpPost(String url,java.util.List<? extends Header> headers,HttpEntity httpEntity,boolean recuive) {
        return doHttpRequest(METHOD_POST, url, headers, httpEntity, recuive);
    }
    
    public HttpResponseData doHttpRequest(int method, String url, java.util.List<? extends Header> headers, HttpEntity httpEntity,boolean recuive) {
//        WriteLog("Requesting :[%s] %s ...", method, url);
    	
    	Log.e("HttpUtils", "url:"+url);
        org.apache.http.client.methods.HttpRequestBase request=null;
        if (METHOD_GET==method) {
            request = new org.apache.http.client.methods.HttpGet(url);
        } else if(METHOD_POST==method) {
            org.apache.http.client.methods.HttpPost post = new org.apache.http.client.methods.HttpPost(url);
            post.setEntity(httpEntity);
            request = post;
        } else {
            Log.e("DizSoft", "doHttpRequest : Invalid method(1=Get,2=Post).");
        }
        if (headers != null) {
            for (Header header : headers) {
                request.addHeader(header);
            }
        }
        try {
            HttpResponse hresponse = httpClient.execute(request);
            if(hresponse!=null) {
                HttpResponseData r = new HttpResponseData(request,hresponse);
//                if(LogEnable) WriteLog("Response:%s", r.toString());
                if(recuive) {
                    String ct = r.getContentType();
                    if(ct!=null && ct.startsWith("text/html")) {
                        Matcher m = PATTERN_META_REDIR.matcher(new String(r.getData()));
                        if(m.find()) {
                            return doHttpRequest(METHOD_GET,m.group(1),null,null,recuive);
                        }
                    }
                }
                response = r;
            }
        } catch(Exception e) {
        	response = new HttpResponseData(e);
            Log.w("DizSoft", "doHttpRequest failed.",e);
            if(LogEnable) {
	            java.io.StringWriter sWriter = new java.io.StringWriter();
	            java.io.PrintWriter pWriter = new java.io.PrintWriter(sWriter);
	            e.printStackTrace(pWriter);
//	            WriteLog("doHttpRequest:[%s] %s failed:%s", method, url, sWriter.toString());
            }
        }
        return response;
    }
    
//    public static void WriteLog(String format, Object... args) {
//        if (LogEnable) {
//        	StringBuilder sb = new StringBuilder();
//        	sb.append(Utils.Date2StringNow("yyyy-MM-dd hh:mm:ss")).append(" ").append(String.format(format, args)).append('\n');
//            java.io.FileOutputStream fos = null;
//            if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//                return;
//            }
//            try {
//                String path = android.os.Environment.getExternalStorageDirectory() + LogPathName;
//                java.io.File file = new java.io.File(path);
//                file.getParentFile().mkdirs();
//                fos = new java.io.FileOutputStream(file, true);
//                fos.write(sb.toString().getBytes("UTF-8"));
//            } catch (Exception e) {
//                Log.i("HttpUtil", "Failed saving log file.", e);
//            } finally {
//                try { if(fos != null) fos.close(); } catch (Exception e) {}
//            }
//        }
//    }

    public static class HttpResponseData {
        private HttpResponse response;
        private byte[] data;
        private String entityCharset;
        private Exception exception;

        private HttpResponseData(Exception ex) {
        	this.exception = ex;
        }
        
        private HttpResponseData(org.apache.http.client.methods.HttpRequestBase request,HttpResponse response) {
            this.response = response;
        }
/*
        public void close() {
            if(request!=null) request.abort();
        }
*/
        public Exception getException() {
        	return exception;
        }
        
        public int getCode() {
            if(response!=null) {
                try { return response.getStatusLine().getStatusCode(); }catch(Exception e){}
            }
            return -1;
        }
        
        public String getContentType() {
            return getHeaderValue("Content-Type");
        }
        
        public Header[] getAllHeaders() {
            return response==null?null:response.getAllHeaders();
        }
        
        public Header[] getHeaders(String header) {
            return response==null?null:response.getHeaders(header);
        }
        
        public Header getHeader(String header) {
            return response==null?null:response.getFirstHeader(header);
        }
        
        public String getHeaderValue(String header) {
            Header h = getHeader(header);
            return h==null?null:h.getValue();
        }
        
        public Header getLastHeader(String header) {
            return response==null?null:response.getLastHeader(header);
        }
        
        public String getLastHeaderValue(String header) {
            Header h = getLastHeader(header);
            return h==null?null:h.getValue();
        }

        private void loadAllData() {
            if(data==null && response!=null) {
                HttpEntity entity = response.getEntity();
                if(entity!=null) {
                    try {
                        data = org.apache.http.util.EntityUtils.toByteArray(entity);
                        entityCharset = org.apache.http.util.EntityUtils.getContentCharSet(entity);
                    } catch(Exception e) {
                        Log.w("DizSoft", "getData", e);
                    } finally {
                        try {entity.consumeContent();}catch(Exception e){}
                    }
                }
            }
        }
        
        public byte[] getData() {
            loadAllData();
            return data;
        }
        
        public String getDataString() {
            byte[] buf = getData();
            if(buf!=null) {
                if (entityCharset == null) {
                    Matcher m = PATTERN_META_ENCODING.matcher(new String(buf));
                    if (m.find()) {
                        entityCharset = m.group(1);
                    } else {
                        entityCharset = "UTF-8";
                    }
                }
                try { return new String(buf,entityCharset); }catch(Exception e){Log.w("DizSoft", "getDataString", e);}
            }
            return null;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String ct = getContentType();
            sb.append("Code:").append(getCode())
              .append(",ContentType:").append(ct);
            if(ct!=null && ct.startsWith("text/")) {
                sb.append(",Data:").append(getDataString());
            } else {
                sb.append(",DataLength:").append(getData().length).append("; DATA:").append(getDataString());
            }
            return sb.toString();
        }
    }
    
    public static class EasySSLSocketFactory implements SocketFactory, LayeredSocketFactory {

        private SSLContext sslcontext = null;

        private static SSLContext createEasySSLContext() throws IOException {
            try {
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, new TrustManager[] { new EasyX509TrustManager(null) }, null);
                return context;
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }

        private SSLContext getSSLContext() throws IOException {
            if (this.sslcontext == null) {
                this.sslcontext = createEasySSLContext();
            }
            return this.sslcontext;
        }

        /**
         * @see org.apache.http.conn.scheme.SocketFactory#connectSocket(java.net.Socket,
         *      java.lang.String, int, java.net.InetAddress, int,
         *      org.apache.http.params.HttpParams)
         */
        public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort,
                HttpParams params) throws IOException, ConnectTimeoutException {
            int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
            int soTimeout = HttpConnectionParams.getSoTimeout(params);

            InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
            SSLSocket sslsock = (SSLSocket) ((sock != null) ? sock : createSocket());

            if ((localAddress != null) || (localPort > 0)) {
                // we need to bind explicitly
                if (localPort < 0) {
                    localPort = 0; // indicates "any"
                }
                InetSocketAddress isa = new InetSocketAddress(localAddress, localPort);
                sslsock.bind(isa);
            }

            sslsock.connect(remoteAddress, connTimeout);
            sslsock.setSoTimeout(soTimeout);
            return sslsock;

        }

        /**
         * @see org.apache.http.conn.scheme.SocketFactory#createSocket()
         */
        public Socket createSocket() throws IOException {
            return getSSLContext().getSocketFactory().createSocket();
        }

        /**
         * @see org.apache.http.conn.scheme.SocketFactory#isSecure(java.net.Socket)
         */
        public boolean isSecure(Socket socket) throws IllegalArgumentException {
            return true;
        }

        /**
         * @see org.apache.http.conn.scheme.LayeredSocketFactory#createSocket(java.net.Socket,
         *      java.lang.String, int, boolean)
         */
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
            // return getSSLContext().getSocketFactory().createSocket();
        }

        // -------------------------------------------------------------------
        // javadoc in org.apache.http.conn.scheme.SocketFactory says :
        // Both Object.equals() and Object.hashCode() must be overridden
        // for the correct operation of some connection managers
        // -------------------------------------------------------------------

        public boolean equals(Object obj) {
            return ((obj != null) && obj.getClass().equals(EasySSLSocketFactory.class));
        }

        public int hashCode() {
            return EasySSLSocketFactory.class.hashCode();
        }

    }

    private static class EasyX509TrustManager implements X509TrustManager {

        private X509TrustManager standardTrustManager = null;

        /**
         * Constructor for EasyX509TrustManager.
         */
        public EasyX509TrustManager(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
            super();
            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(keystore);
            TrustManager[] trustmanagers = factory.getTrustManagers();
            if (trustmanagers.length == 0) {
                throw new NoSuchAlgorithmException("no trust manager found");
            }
            this.standardTrustManager = (X509TrustManager) trustmanagers[0];
        }

        /**
         * @see javax.net.ssl.X509TrustManager#checkClientTrusted(X509Certificate[],
         *      String authType)
         */
        public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
            // standardTrustManager.checkClientTrusted( certificates, authType );
        }

        /**
         * @see javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate[],
         *      String authType)
         */
        public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
            // if ( ( certificates != null ) && ( certificates.length == 1 ) )
            // {
            // certificates[0].checkValidity();
            // }
            // else
            // {
            // standardTrustManager.checkServerTrusted( certificates, authType );
            // }
        }

        /**
         * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
         */
        public X509Certificate[] getAcceptedIssuers() {
            return this.standardTrustManager.getAcceptedIssuers();
        }
    }
}
