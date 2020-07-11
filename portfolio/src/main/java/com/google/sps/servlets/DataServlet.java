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
  
  private List<String> greetings;

  @Override
  public void init() {
    greetings = new ArrayList<>();
    greetings.add("Nice to meet you");
    greetings.add("Have a good day");
    greetings.add("If you believe, you will achieve, and if you achieve, you will succeed.");
    greetings.add("Have fun");
    greetings.add("I like math");
    greetings.add("My favourite animals are dogs");
    greetings.add("My favourite sport is swimming");
    greetings.add("Sleeping is important, please read 'Why We Sleep' by Matthew Walker");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String greeting = greetings.get((int) (Math.random() * greetings.size()));
    Gson gson = new Gson();
    String json = gson.toJson(greeting);

    response.setContentType("text/html;");
    response.getWriter().println(json);
  }
}
