function selectMethod(element) {
	
    var methods = document.querySelectorAll('.method');
    methods.forEach(function(method) {
        method.classList.remove('active');
    });

    element.classList.add('active');
}
