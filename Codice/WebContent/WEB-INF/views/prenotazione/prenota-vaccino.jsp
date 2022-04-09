<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<style>
		@media screen and (max-width: 767px){
	        #corpoPrincipale {
	            height: 650px;
	        }
	    }
	</style>

	<script>
		loadNavbar();
	
		$("#elenco").hide();
	    $("#button").click(() => {
	    	$("#elenco").slideToggle("fast");
	    });
	
	    $.get("./utilities/json/province.json", (data, status) => {
	        if(status == "success") {
	            data.sort(ordina);
	            data.forEach(el => {
	          		$("#provincia").append("<option value="+el.id+">"+el.nome+"</option>");
	      		});
	        }
	    });
	    
	    if(!$("#data").attr("min"))
	    	$("#data").attr("min", oggi());
	    
	    
	    $("form").submit(event => {
	    	event.preventDefault();
	    	$("#corpoPrincipale").load("/SolaroProject/ElencoPosti", {
	    		provincia: $("#provincia").val(),
	    		data: $("#data").val()
	    	});
	    	loading();
	    });
	    
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center px-0" style="height: inherit;">
		<h1 class="titolo-principale mb-5" style="height: 1.3em">Benvenuto</h1>
        <div class="col-sm-12 col-12">
        	<div class="row justify-content-center mt-5">
            	<button id="button" class="btn btn-secondary w-75 mt-5">Prenota ora il tuo vaccino</button>
            </div>
            <form id="elenco" class="mt-4">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="form-group mb-0 pt-2">
	                    	<label class="lead text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="provincia">
	                            Provincia di preferenza
	                        </label>
	                        <select id="provincia" name="provincia" class="form-control" required>
	                            <option value=""></option>
	                        </select>
	                    </div>
	                    <div class="form-group mb-0 pt-2 px-5">
	                    	<label class="lead text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="data">
	                            Data
	                        </label>
	                        <input id="data" type="date" name="data"class="form-control" min="${posto.data}" required/>
	                    </div>
	                    <div class = "row justify-content-center px-5" style="padding-top: 44px;">
	                  		<button class="btn btn-primary">Cerca</button>
	                    </div>
	                </div>
	          	</div>
	        </form>
	    </div>
	</div>
</body>
</html>