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

import java.io.File;
import java.util.Date;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author stephen
 */
public class GitService implements VcsService{
    @Autowired
    private IrcService irc;
    
    @Value("${git.repo.url}")
    private String repoUrl;
    
    @Value("${git.repo.remote}")
    private String remote; 
    
    @Value("${git.repo.cooldown}")
    private int cooldown;
    
    private Long mostRecent;
    
    @Override
    public void run() {
        try{
            mostRecent = new Date().getTime();
            File gitDir = new File(repoUrl);
            
            Git git = Git.open(gitDir);
            
            do{
                echoNewCommits(git);
                Thread.sleep(cooldown);
            }while(true);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void echoNewCommits(Git git) throws GitAPIException {
        if(remote != null && !remote.isEmpty()){
            git.pull()
                    .setRemote(remote)
                    .setRemoteBranchName("master")
                    .call();
        }
        Long newMostRecent = 0l;
        for(RevCommit commit : git.log().call()){
            if(commit.getCommitTime() > newMostRecent){
                newMostRecent = (long)commit.getCommitTime();
            }
            if(mostRecent > commit.getCommitTime()){
                break;
            }
            String msg = String.format("Git commit: %s: %s",
                    commit.getCommitterIdent().getName(),
                    commit.getFullMessage());
            irc.sendMessage(msg);
        }
        mostRecent = newMostRecent == 0
                ? mostRecent
                : newMostRecent;
    }
    
}
