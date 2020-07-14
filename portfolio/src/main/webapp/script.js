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

/**
 * Adds a random greeting to the page.
 */
/*function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!','How you doin\'?'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function getRandomGreetingUsingArrowFunctions() {
    console.log('fetch Json string from server and add to page');
  fetch('/data').then(response => response.json()).then((greeting) => {
    document.getElementById('greeting-container').innerText = greeting;
  });
}
*/
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

/** Creates a chart and adds it to the page. */
function drawChart() {
  const data = new google.visualization.DataTable();
  data.addColumn('string', 'To-Do');
  data.addColumn('number', 'Time');
        data.addRows([
          ['SPS', 2],
          ['Work', 6],
          ['Workout', 1],
          ['Read', 1],
          ['Study', 3],
          ['Eat', 3],
          ['Sleep', 8]
        ]);

  const options = {
    'title': 'Daily Schedule',
    'width':500,
    'height':400
  };

  const chart = new google.visualization.PieChart(
      document.getElementById('chart-container'));
  chart.draw(data, options);
}

function getNumberGame() {
  fetch('/data').then(response => response.json()).then((tasks) => {

    // Build the list of history entries.
    const historyEl = document.getElementById('history');
    tasks.forEach((title) => {
      historyEl.appendChild(createListElement(title));
    });
  });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

