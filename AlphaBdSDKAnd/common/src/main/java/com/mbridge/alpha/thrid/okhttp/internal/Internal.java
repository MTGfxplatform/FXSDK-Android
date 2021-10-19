/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mbridge.alpha.thrid.okhttp.internal;

import java.io.IOException;
import java.net.Socket;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import com.mbridge.alpha.thrid.okhttp.Address;
import com.mbridge.alpha.thrid.okhttp.Call;
import com.mbridge.alpha.thrid.okhttp.ConnectionPool;
import com.mbridge.alpha.thrid.okhttp.ConnectionSpec;
import com.mbridge.alpha.thrid.okhttp.Headers;
import com.mbridge.alpha.thrid.okhttp.OkHttpClient;
import com.mbridge.alpha.thrid.okhttp.Request;
import com.mbridge.alpha.thrid.okhttp.Response;
import com.mbridge.alpha.thrid.okhttp.Route;
import com.mbridge.alpha.thrid.okhttp.internal.cache.InternalCache;
import com.mbridge.alpha.thrid.okhttp.internal.connection.RealConnection;
import com.mbridge.alpha.thrid.okhttp.internal.connection.RouteDatabase;
import com.mbridge.alpha.thrid.okhttp.internal.connection.StreamAllocation;

/**
 * Escalate internal APIs in {@code okhttp3} so they can be used from OkHttp's implementation
 * packages. The only implementation of this interface is in {@link OkHttpClient}.
 */
public abstract class Internal {

  public static void initializeInstanceForTests() {
    // Needed in tests to ensure that the instance is actually pointing to something.
    new OkHttpClient();
  }

  public static Internal instance;

  public abstract void addLenient(Headers.Builder builder, String line);

  public abstract void addLenient(Headers.Builder builder, String name, String value);

  public abstract void setCache(OkHttpClient.Builder builder, InternalCache internalCache);

  public abstract RealConnection get(ConnectionPool pool, Address address,
      StreamAllocation streamAllocation, Route route);

  public abstract boolean equalsNonHost(Address a, Address b);

  public abstract Socket deduplicate(
      ConnectionPool pool, Address address, StreamAllocation streamAllocation);

  public abstract void put(ConnectionPool pool, RealConnection connection);

  public abstract boolean connectionBecameIdle(ConnectionPool pool, RealConnection connection);

  public abstract RouteDatabase routeDatabase(ConnectionPool connectionPool);

  public abstract int code(Response.Builder responseBuilder);

  public abstract void apply(ConnectionSpec tlsConfiguration, SSLSocket sslSocket,
      boolean isFallback);

  public abstract boolean isInvalidHttpUrlHost(IllegalArgumentException e);

  public abstract StreamAllocation streamAllocation(Call call);

  public abstract @Nullable IOException timeoutExit(Call call, @Nullable IOException e);

  public abstract Call newWebSocketCall(OkHttpClient client, Request request);
}
