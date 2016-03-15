/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myWindow;

function openWin(url) {
     document.getElementById("enlaceApp").click();
     //return false;
}
function openWin2(url) {
    myWindow = window.open(url, "myWindow", "width=400, height=200");
    try {
        myWindow.focus();
    }
    catch (e) {
        alert("Tu navegador tiene bloqueado las ventanas emergentes. Por favor, desactivelo antes de continuar.");
    }
    var w = screen.width - 50;
    var h = screen.height - 100;
    var left = (screen.width / 2) - (w / 2);
    var top = (screen.height / 2) - (h / 2);
    myWindow.moveTo(top, left);
    myWindow.resizeTo(w, h);
}
function largoModal() {
    return screen.height - 100;
}
function anchoModal() {
    return screen.width - 50;
}
function closeWin() {
    myWindow.close();
}


