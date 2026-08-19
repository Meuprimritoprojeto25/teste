(function () {
  "use strict";
  var button = document.querySelector("[data-menu]");
  var sidebar = document.getElementById("sidebar");
  if (button && sidebar) {
    button.onclick = function () { sidebar.classList.toggle("open"); };
  }
  var date = document.getElementById("data");
  if (date && !date.value) {
    var now = new Date();
    date.value = now.getFullYear() + "-" + ("0" + (now.getMonth() + 1)).slice(-2) + "-" + ("0" + now.getDate()).slice(-2);
  }
  var flash = document.querySelector(".flash");
  if (flash) {
    window.setTimeout(function () { flash.style.opacity = "0"; flash.style.transition = "opacity .4s"; }, 4500);
  }
}());