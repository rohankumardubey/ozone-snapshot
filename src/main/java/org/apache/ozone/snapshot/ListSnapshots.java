/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.ozone.snapshot;

import picocli.CommandLine;

@CommandLine.Command(name="list-snapshots",
    description = "Command to list ozone snapshots.",
    versionProvider = SnapshotVersionProvider.class,
    mixinStandardHelpOptions = true)
public class ListSnapshots implements Runnable {

  private final CommandLine commandLine;

  @CommandLine.Option(names = {"-p", "--dbPath"},
      description = "Path to store db.")
  private String path = "/tmp";


  private static ListSnapshots getInstance() {
    return new ListSnapshots();
  }

  private ListSnapshots() {
    this.commandLine = new CommandLine(this);
  }

  @Override
  public void run() {
    try {
      final OzoneManager ozoneManager = new OzoneManager(path);
      ozoneManager.getSnapshotManager().getAllSnapshots(null, null)
          .forEach(System.out::println);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private int execute(final String[] args) {
    return commandLine.execute(args);
  }

}
