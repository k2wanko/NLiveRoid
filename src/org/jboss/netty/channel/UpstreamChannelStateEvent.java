/*
 * Copyright 2009 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.channel;

import org.jboss.netty.channel.socket.nio.NioSocketChannel;

import android.util.Log;

/**
 * The default upstream {@link ChannelStateEvent} implementation.
 *
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 *
 * @version $Rev: 2080 $, $Date: 2010-01-26 18:04:19 +0900 (Tue, 26 Jan 2010) $
 *
 */
public class UpstreamChannelStateEvent implements ChannelStateEvent {

    private final NioSocketChannel channel;
    private final ChannelState state;
    private final Object value;

    /**
     * Creates a new instance.
     */
    public UpstreamChannelStateEvent(
            NioSocketChannel channel, ChannelState state, Object value) {
        this.channel = channel;
        this.state = state;
        this.value = value;
    }

    public NioSocketChannel getChannel() {
        return channel;
    }

    public ChannelFuture getFuture() {
        return new SucceededChannelFuture(getChannel());
    }

    public ChannelState getState() {
        return state;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        String channelString = getChannel().toString();
        StringBuilder buf = new StringBuilder(channelString.length() + 64);
        buf.append(channelString);
        switch (getState()) {
        case OPEN:
            if (Boolean.TRUE.equals(getValue())) {
                buf.append(" OPEN");
            } else {
                buf.append(" CLOSED");
            }
            break;
        case BOUND:
            if (getValue() != null) {
                buf.append(" BOUND: ");
                buf.append(getValue());
            } else {
                buf.append(" UNBOUND");
            }
            break;
        case CONNECTED:
            if (getValue() != null) {
                buf.append(" CONNECT: ");
                buf.append(getValue());
            } else {
                buf.append(" DISCONNECTED");
            }
            break;
        case INTEREST_OPS:
            buf.append(" INTEREST_CHANGED");
            break;
        default:
            buf.append(getState().name());
            buf.append(": ");
            buf.append(getValue());
        }
        return buf.toString();
    }
}
