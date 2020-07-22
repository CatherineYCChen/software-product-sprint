// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")


public class DataServlet extends HttpServlet {

DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
boolean game = true;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    Query query = new Query("Task").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    
    //Create a list of guesses
    List<String> tasks = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
    
      //Get title - i.e. guess
      String title = (String) entity.getProperty("title");

      //Add guess to list of guesses
      tasks.add(title);
    }
    Gson gson = new Gson();
    response.setContentType("application/json;");
    //print out list of guesses
    response.getWriter().println(gson.toJson(tasks));

  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String title = request.getParameter("player-choice");
    long timestamp = System.currentTimeMillis();
    
    Entity guessEntity = new Entity("Task");
    guessEntity.setProperty("title", title);
    guessEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(guessEntity);

    int playerChoice = getPlayerChoice(request);

    /*if (playerChoice == 7) {
      response.setContentType("text/html;");
      response.getWriter().println("You're correct!");
      return;
    }
    */
    
    if (playerChoice == -1) {
      response.setContentType("text/html;");
      response.getWriter().println("Please enter an integer between 1 and 10.");
    return;
    }
    /*
    if (playerChoice != -1 && playerChoice != 7){
      response.setContentType("text/html;");
      response.getWriter().println("My favourite number from one to ten is 7.");
    return;
    }
    */

    response.sendRedirect("/index.html");
            
  }
  /** Returns the choice entered by the player, or -1 if the choice was invalid. */
  
  private int getPlayerChoice(HttpServletRequest request) {
    // Get the input from the form.
    String playerChoiceString = request.getParameter("player-choice");

    // Convert the input to an int.
    int playerChoice;
    try {
      playerChoice = Integer.parseInt(playerChoiceString);
    } catch (NumberFormatException e) {
      System.err.println("Could not convert to int: " + playerChoiceString);
      return -1;
    }

    // Check that the input is between 1 and 10.
    if (playerChoice < 1 || playerChoice > 10) {
      System.err.println("Player choice is out of range: " + playerChoiceString);
      return -1;
    }

    return playerChoice;
  }
}

