/**
 * 
 */

function updateLedger(){
    ledgers.pop();
    ledgers.unshift(ledgerCached);

    var table = document.getElementById("blocktable");

    var htmlText = "";
    for(item in ledgers){
       // var tr = table.insertRow();
        htmlText += "<tr>";
        for(key in ledgers[item]){
            htmlText += "<td>" +  ledgers[item][key]  + "</td>";
        }
    htmlText += "</tr>";
    }
    $("#blocktable tbody").html(htmlText);
}