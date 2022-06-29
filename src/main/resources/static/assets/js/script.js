document.getElementById("click").style.display = "none";
function myFunction() {  
document.getElementById("click").style.display = "flex";
 }
//
// $(window).scroll(function() {
//     $('html, body').animate({
//         scrollTop: $("#myDiv").offset().top
//     }, 9000);
// });

const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
const today  = new Date();

const date = ('0' + today.getDate()).slice(-2);
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const year = today.getFullYear();
const time = `${date}/${month}/${year}`;

today.toLocaleDateString("en-US");
document.getElementById("demo").innerHTML = time.toString();
