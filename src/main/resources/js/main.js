var redOverview = 'images/Home/mage_dashboard-fill.png'
var redEmployees = 'images/raphael_people.png'
var redCoaches = 'images/fluent_person-20-filled.png'
var redInvoices = 'images/basil_invoice-solid.png'
var redSessionHistory = 'images/tabler_history.png'
var redChats = 'images/Red  chat.png'

var normalOverview = 'images/mage_dashboard-fill.png'
var normalEmployees = 'images/Home/raphael_people.png'
var normalCoaches = 'images/Home/Vector.png'
var normalInvoices = 'images/Home/basil_invoice-solid.png'
var normalSessionHistory = 'images/Home/tabler_history.png'
var normalChats = 'images/Home/flowbite_messages-solid.png'
function employers(){
    $('#icon1').attr('src',redEmployees)
    $('.content').load('employees.html', function() {})
    $('#employees').addClass('active')
    $('#span1').removeClass('mnm')
    $('#icon').attr('src',normalOverview)
    $('#span2').addClass('mnm')
   
    $('#dashboard').removeClass('active')
    $('#span3').removeClass('mnm')
    $('#coaches').removeClass('active')
    $('#icon2').attr('src',normalCoaches)
    $('#invoices').removeClass('active')
    $('#icon3').attr('src',normalInvoices)
    $('#span4').removeClass('mnm')
    $('#icon4').attr('src',normalSessionHistory)
    $('#sessionHistory').removeClass('active')
    $('#span5').removeClass('mnm')
}
function overview(){
    $('#icon').attr('src',redOverview)
    $('.content').load('index.html', function() {})
    $('#dashboard').addClass('active')
    $('#span1').addClass('mnm')
   
    $('#span2').removeClass('mnm')
    $('#icon1').attr('src',normalEmployees)
    $('#employees').removeClass('active')
    $('#span3').removeClass('mnm')
    $('#coaches').removeClass('active')
    $('#icon2').attr('src',normalCoaches)
    $('#invoices').removeClass('active')
    $('#icon3').attr('src',normalInvoices)
    $('#span4').removeClass('mnm')
    $('#icon4').attr('src',normalSessionHistory)
    $('#sessionHistory').removeClass('active')
    $('#span5').removeClass('mnm')
} 
function coaches(){
    $('#icon2').attr('src',redCoaches)
    $('.content').load('coaches.html', function() {})
    $('#coaches').addClass('active')
    $('#span1').removeClass('mnm')
    
    $('#span2').removeClass('mnm')
    $('#icon1').attr('src',normalEmployees)
    $('#employees').removeClass('active')
    $('#dashboard').removeClass('active')
    $('#icon').attr('src',normalOverview)
    $('#span3').addClass('mnm')
    $('#invoices').removeClass('active')
    $('#icon3').attr('src',normalInvoices)
    $('#span4').removeClass('mnm')
    $('#icon4').attr('src',normalSessionHistory)
    $('#sessionHistory').removeClass('active')
    $('#span5').removeClass('mnm')
}

function invoices(){
    $('#icon3').attr('src',redInvoices)
    $('.content').load('invoices.html', function() {})
    $('#invoices').addClass('active')
    $('#span3').removeClass('mnm')
    $('#span1').removeClass('mnm')
    $('#icon2').attr('src',normalCoaches)
    
    $('#span2').removeClass('mnm')
    $('#icon1').attr('src',normalEmployees)
    $('#employees').removeClass('active')
    $('#dashboard').removeClass('active')
    $('#coaches').removeClass('active')
    $('#icon').attr('src',normalOverview)
    $('#span4').addClass('mnm')
    $('#icon4').attr('src',normalSessionHistory)
    $('#sessionHistory').removeClass('active')
    $('#span5').removeClass('mnm')
}

function sessionHistory(){
    $('.content').load('sessionHistory.html', function() {})
    $('#sessionHistory').addClass('active')
    $('#span4').removeClass('mnm')
    $('#span1').removeClass('mnm')
    $('#span3').removeClass('mnm')
    $('#icon2').attr('src',normalCoaches)
    $('#icon3').attr('src',normalInvoices)
    $('#icon4').attr('src',redSessionHistory)
    $('#span2').removeClass('mnm')
    $('#icon1').attr('src',normalEmployees)
    $('#employees').removeClass('active')
    $('#dashboard').removeClass('active')
    $('#invoices').removeClass('active')
    $('#coaches').removeClass('active')
    $('#icon').attr('src',normalOverview)
    $('#span5').addClass('mnm')
}
$('#upSession').on('click', function() {
    $('.content').load('upcomingSessions.html', function() {}) 
})
$('#ongBookings').on('click', function() {
    $('.content').load('ongoingBookings.html', function() {}) 
})
function coachesInformation() {
    $('#icon2').attr('src',redCoaches)
    $('.content').load('coachesInformation.html', function() {})
    $('#coaches').addClass('active')
    $('#span1').removeClass('mnm')
   
    $('#span2').removeClass('mnm')
    $('#icon1').attr('src',normalEmployees)
    $('#employees').removeClass('active')
    $('#dashboard').removeClass('active')
    $('#icon').attr('src',normalOverview)
    $('#span3').addClass('mnm')
    $('#invoices').removeClass('active')
    $('#icon3').attr('src',normalInvoices)
    $('#span4').removeClass('mnm')
    $('#icon4').attr('src',normalSessionHistory)
    $('#sessionHistory').removeClass('active')
    $('#span5').removeClass('mnm')
    
}
$('#adEmployees').on('click',function(){
    $('.modal-content').load('addEmployeesModal.html',function(){})
    $('.modal-dialog').removeClass('modal-xl')
})
$('#confermation').on('click',function(){
    $('.modal-content').load('addedEmployeeConfirmation.html',function(){})
    $('.modal-dialog').addClass('modal-dialog-centered')
    $('.modal-dialog').removeClass('modal-xl')
})
$('#book').on('click',function(){
    $('.modal-content').load('bookCoach.html',function(){})
    $('.modal-dialog').addClass('modal-lg')
    $('.modal-dialog').removeClass('modal-xl')
})
$('#Preview').on('click',function(){
    $('.modal-content').load('bookCoachPreview.html',function(){})
    $('.modal-dialog').removeClass('modal-xl')
})
$('#bookConfirm').on('click',function(){
    $('.modal-content').load('bookConfirmation.html',function(){})
    $('.modal-dialog').removeClass('modal-xl')
})
$('#btonBooking').on('click', function() {
    $('#icon').attr('src',redOverview)
    $('.content').load('ongoingBookings.html', function() {}) 
    $('#dashboard').addClass('active')
    $('#span1').addClass('mnm')
    
    $('#span2').removeClass('mnm')
    $('#icon1').attr('src',normalEmployees)
    $('#employees').removeClass('active')
    $('#span3').removeClass('mnm')
    $('#coaches').removeClass('active')
    $('#icon2').attr('src',normalCoaches)
    $('#invoices').removeClass('active')
    $('#icon3').attr('src',normalInvoices)
    $('#span4').removeClass('mnm')
    $('#icon4').attr('src',normalSessionHistory)
    $('#sessionHistory').removeClass('active')
    $('#span5').removeClass('mnm')
})
$('#paInvoice').on('click',function(){
    $('.modal-content').load('pendingInvoice.html',function(){})
    $('.modal-dialog').addClass('modal-lg')
    $('.modal-dialog').removeClass('modal-xl')
})
$('#penInvoice').on('click',function(){
    $('.modal-content').load('paidInvoice.html',function(){})
    $('.modal-dialog').addClass('modal-lg')
})
$('#otoSession').on('click',function(){
    $('.modal-content').load('onetooneSession.html',function(){})
    $('.modal-dialog').removeClass('modal-lg')
    $('.modal-dialog').removeClass('modal-xl')
})
$('#otmSession').on('click',function(){
    $('.modal-content').load('onetomanySession.html',function(){})
    $('.modal-dialog').removeClass('modal-lg')
    $('.modal-dialog').removeClass('modal-xl')
})
$('#rating').on('click',function(){
    $('.modal-content').load('coachesReviews.html',function(){})
    $('.modal-dialog').removeClass('modal-lg')
})
$('#rating1').on('click',function(){
    $('.modal-content').load('coachesReviews.html',function(){})
    $('.modal-dialog').removeClass('modal-lg')
    $('.modal-dialog').removeClass('modal-xl')
})
$('#rating2').on('click',function(){
    $('.modal-content').load('coachesReviews.html',function(){})
    $('.modal-dialog').removeClass('modal-lg')
    $('.modal-dialog').removeClass('modal-xl')
})
function coachAgreement(){
    $('.modal-content').load('coachAgreement.html',function(){})
    $('.modal-dialog').removeClass('modal-lg')
    $('.modal-dialog').addClass('modal-xl')
}
function viewDetail(){
    $('.modal-content').load('ongoingBookingViewDetail.html',function(){})
    $('.modal-dialog').removeClass('modal-lg')
    $('.modal-dialog').removeClass('modal-xl')
}
function schedule(){
    $('.modal-content').load('scheduleSession.html',function(){})
    $('.modal-dialog').addClass('modal-lg')
    $('.modal-dialog').removeClass('modal-xl')
}












