function getStatusColor(status) {
  switch (status) {
    case "creada":
      return "color-blue";
    case "facturada":
      return "color-orange";
    case "enviada":
      return "color-purple";
    case "entregada-cobrada":
      return "color-green";
    case "cancelada":
      return "color-red";
    default:
      return "";
  }
}

$(document).ready(function () {
      if ($('#success-message').length > 0) {
        toastr.success($('#success-message').text());
      }
});

$(document).ready(function () {
      if ($('#error-message').length > 0) {
        toastr.error($('#error-message').text());
      }
});

$(document).ready(function() {
      // Cambiar la pestaña activa al hacer clic en una pestaña
      $('.nav-tabs a').click(function() {
        $(this).tab('show');
      });

      // Validar contraseñas iguales y condiciones
      $('#submitBtn').click(function(event) {
        var password = $('#password').val();
        var confirmPassword = $('#confirmPassword').val();

        if (password !== confirmPassword) {
          toastr.error('Las contraseñas no coinciden.');
          event.preventDefault();
        }

        var regexUppercase = new RegExp('[A-Z]');
        var regexSpecialChar = new RegExp('[@#$%&*.]');
        var regexLetterCount = new RegExp('[a-zA-Z]{5,}');
        var regexNumberCount = new RegExp('[0-9].*[0-9]');

        if (!regexUppercase.test(password)) {
          toastr.error('La contraseña debe contener al menos una letra mayúscula.');
          event.preventDefault();
        }

        if (!regexSpecialChar.test(password)) {
          toastr.error('La contraseña debe contener al menos un carácter especial.');
          event.preventDefault();
        }

        if (!regexLetterCount.test(password)) {
          toastr.error('La contraseña debe contener al menos 5 letras.');
          event.preventDefault();
        }

        if (!regexNumberCount.test(password)) {
          toastr.error('La contraseña debe contener al menos 2 números.');
          event.preventDefault();
        }
      });
    });

// Lógica para cargar más categorías
  window.addEventListener('scroll', function() {
    // Verificar si se ha llegado al final de la página
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
      // Realizar una solicitud AJAX o fetch para obtener más datos de categorías del servidor
      // y agregarlos dinámicamente a la lista de categorías
      fetch('/obtener-categorias?pagina=2')
        .then(response => response.json())
        .then(data => {
          // Obtener referencia al elemento de lista de categorías
          const productCategories = document.querySelector('.product-categories');

          // Agregar las nuevas categorías a la lista
          data.forEach(category => {
            var newCategory = document.createElement('li');
            newCategory.innerHTML = '<p>' + category.name + '</p>';
            productCategories.appendChild(newCategory);
          });
        });
    }
  });

$(document).ready(function () {
        $('#showPassword').click(function () {
            var passwordField = $('#password');
            var confirmPasswordField = $('#confirmPassword');
            if ($(this).is(':checked')) {
                passwordField.attr('type', 'text');
                confirmPasswordField.attr('type', 'text');
            } else {
                passwordField.attr('type', 'password');
                confirmPasswordField.attr('type', 'password');
            }
        });
    });

    function valideKey(evt){

    // code is the decimal ASCII representation of the pressed key.
    var code = (evt.which) ? evt.which : evt.keyCode;

    if(code==8) { // backspace.
      return true;
    } else if(code>=48 && code<=57) { // is a number.
      return true;
    } else{ // other keys.
      return false;
    }
}

function checkPasswordStrength(password) {
  var conditions = {
    uppercase: /[A-Z]/.test(password),
    specialchar: /[@#$%&*.]/.test(password),
    lettercount: /[a-zA-Z]{5,}/.test(password),
    numbercount: (password.match(/\d/g) || []).length >= 2
  };

  var passwordConditions = document.getElementById("password-conditions");

  for (var condition in conditions) {
    var conditionElement = document.getElementById(condition);

    if (conditions[condition]) {
      conditionElement.classList.add("valid");
      conditionElement.classList.remove("invalid");
    } else {
      conditionElement.classList.add("invalid");
      conditionElement.classList.remove("valid");
    }
  }
}

function eliminar(id){
    swal({
      title: "¿Está seguro de dar de baja?",
      text: "esta acción establecerá al usuario en estado inactivo y no podrá ingresar al sistema!",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    })
    .then((OK) => {
      if (OK) {
        $.ajax({
            url:"/admin/users/delete/"+id,
            success: function(res){
                console.log(res);
            },
        });
        swal("¡Se ha dado de baja al usuario!", {
          icon: "success",
        }).then((ok)=>{
            if(ok){
                location.href="/admin/users/show";
            }
        });
      } else {
        swal("Se ha cancelado la eliminación!");
      }
    });
}





