function loading() {
	$("#corpoPrincipale").html('<div class="row justify-content-center align-items-center" style="height: inherit;"> '  +
                                    ' <img id="loading" src="./images/loading-img.png" alt="loading..."> ' +
                               '</div>');
}


function loadNavbar() {
	$("#navbar").load("/SolaroProject/NavbarDispatcher");
}

function loadInit() {
	loading();
	$("#corpoPrincipale").load("/SolaroProject/InitDispatcher");
}

function loginDispatch(event, num) {
    event.preventDefault();
    loading();
    $("#corpoPrincipale").load("/SolaroProject/LoginDispatcher?num="+num);
}

function loadReportS() {
	event.preventDefault();
	loading();
	$("#corpoPrincipale").load("/SolaroProject/ReportSemplice");
}

function loadRegioni() {
    $.get("/SolaroProject/CentroVaccinale/utilities/json/regioni.json", (data, status) => {
        if(status == "success")
            data.forEach(el => {
                $("#regione").append("<option value="+el.id+">"+el.nome+"</option>");
            });
    });
}

function loadProvince() {
    $("#provincia").html('<option value=""></option>');
    $.get("/SolaroProject/CentroVaccinale/utilities/json/province.json", (data, status) => {
        if(status == "success") {
            data.forEach(el => {
                if(el.id_regione == $(this).val())
                    $("#provincia").append("<option value="+el.id+">"+el.nome+"</option>");
            });
        }
    })
}


function esitoChange() {
    if($(this).val() == "positivo") {
        $("#vaccino").removeAttr("disabled");
        $("#tipo").removeAttr("disabled");
    }
    else {
    	$("#vaccino").val("");
    	$("#tipo").val("");
        $("#vaccino").attr("disabled", true);
        $("#tipo").attr("disabled", true);
    }
}


function vaccinoChange() {
    $("#tipo option").remove();
	if(!$(this).val()) {
		$("#tipo").append("<option value=''></option>");
	}
	else if($(this).val() == "janssen") {
		$("#tipo").append("<option value='unica dose' selected>Unica Dose</option>");
	}
	else {
		$("#tipo").append("<option value=''></option>" +
						  "<option value='prima dose'>Prima Dose</option>" +
						  "<option value='seconda dose'>Seconda Dose</option>");
	}
}

function ordina(a, b) {
    if(a.nome > b.nome)
        return 1
    else if(a.nome < b.nome)
        return -1
    
    return 0;
}


function oggi() {
	let data = new Date().toLocaleDateString().split("/");
	let month = data[1].length == 1 ? "0"+data[1] : data[1];
	let day = data[0].length == 1 ? "0"+data[0] : data[0];
	return data[2]+"-"+month+"-"+day;
}

function checkPassword() {
    $("#confermaPassword").attr("pattern", $("#password").val());
    let pnc = $("#passNonCoincidenti");
    if(pnc.val() != undefined)
        pnc.parent()[0].removeChild(pnc.parent()[0].lastChild);
    let div = document.createElement("div");
    div.className = "invalid-feedback";
    div.id = "passNonCoincidenti";
          div.style.fontWeight = "bold";
          div.style.textShadow = "0 0 3px black";
    div.innerText = "password non coincidenti";
    $("#confermaPassword").parent()[0].append(div);
}