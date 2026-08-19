(function () {
  "use strict";
  var menu = document.getElementById("mobile-menu");
  var sidebar = document.getElementById("sidebar");
  if (menu && sidebar) {
    menu.onclick = function () {
      var open = sidebar.className.indexOf("open") >= 0;
      sidebar.className = open ? "sidebar" : "sidebar open";
      menu.setAttribute("aria-expanded", String(!open));
    };
  }

  var inputs = document.querySelectorAll("[data-confirm]");
  for (var i = 0; i < inputs.length; i++) {
    inputs[i].onclick = function (event) {
      if (!window.confirm(this.getAttribute("data-confirm"))) { event.preventDefault(); }
    };
  }

  var editor = document.getElementById("document-content");
  var counter = document.getElementById("document-counter");
  if (editor && counter) {
    var count = function () { counter.innerHTML = editor.value.length + " caracteres"; };
    editor.onkeyup = count;
    count();
  }

  var today = document.querySelector("[data-today]");
  if (today) {
    var names = ["domingo","segunda-feira","terça-feira","quarta-feira","quinta-feira","sexta-feira","sábado"];
    var now = new Date();
    today.innerHTML = names[now.getDay()] + ", " + ("0" + now.getDate()).slice(-2) + "/" +
      ("0" + (now.getMonth() + 1)).slice(-2) + "/" + now.getFullYear();
  }
}());