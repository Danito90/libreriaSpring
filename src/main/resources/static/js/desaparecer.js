$(document).ready(function () {
  setTimeout(function () {
    $(".desaparecer").fadeOut(1500);
  }, 3000);

  setTimeout(function () {
    $(".aparecer").fadeIn(1500);
  }, 6000);
});

$(document).ready(function () {
  setTimeout(function () {
    $(".desaparecerLento").fadeOut(1500);
  }, 6000);

  setTimeout(function () {
    $(".aparecerLento").fadeIn(1500);
  }, 6000);
});