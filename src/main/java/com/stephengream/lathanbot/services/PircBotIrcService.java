/*
 * Copyright (c) 2015, Stephen Gream
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.stephengream.lathanbot.services;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author stephen
 */
@Service
public class PircBotIrcService extends ListenerAdapter implements IrcService{
    private PircBotX bot;
    
    @Value("${irc.bot.name}")
    private String botName;
    
    @Value("${irc.bot.server.address}")
    private String serverHostname;
    
    @Value("${irc.bot.server.port}")
    private Integer port;
    
    @Value("${irc.bot.channel}")
    private String defaultChannel;
    
    @Value("${irc.bot.prefix}")
    private String prefix;
    
    @Value("${irc.bot.ssl}")
    private Boolean useSSL;
    
    @Override
    public void connect() {
        SocketFactory sock = useSSL 
                ? SSLSocketFactory.getDefault()
                : SocketFactory.getDefault();
        Configuration configuration = new Configuration.Builder()
                .setName(botName)
                .setServer(serverHostname, port)
                .addAutoJoinChannel(defaultChannel)
                .addListener(this)
                .setSocketFactory(sock)
                .buildConfiguration();
        bot = new PircBotX(configuration);
        try{
            bot.startBot();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
    @Override
    public void onMessage(MessageEvent event){
        
    }

    @Override
    public void sendMessage(String content) {
        for(Channel c : bot.getUserBot().getChannels()){
            c.send().message(prefix + content);
        }
    }

    @Override
    public void run() {
        connect();
    }
    
}
