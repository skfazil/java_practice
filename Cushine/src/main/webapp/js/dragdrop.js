function handleDragOver(e) {
  if (e.preventDefault) {
    e.preventDefault();
  }
  this.classList.add('over');
  return false;
}
function handleDragLeave(e) {
	this.classList.remove('over'); 
}
function handleDrop(e) {
  if (e.preventDefault) {
    e.preventDefault();
  }
  	this.classList.remove('over');  
	/* aajax call where you need to update in database */
	/* endsc */
	source.innerHTML = this.innerHTML;
	console.log(source.parents('.cell_room'));
	this.innerHTML ='<div style="padding:15px;text-align:center;"><img src="images/ajax-loading.gif" text-a/></div>';
	this.innerHTML = e.dataTransfer.getData('text');
	source.innerHTML = 'Moved..';
	//alert(this.parent('.cell_room').html());
  }
var fras = document.querySelectorAll('.cell_room');
[].forEach.call(fras, function(fra) {
  fra.addEventListener('dragstart', handleDragStart, false);
  fra.addEventListener('dragover', handleDragOver, false);
  fra.addEventListener('dragleave', handleDragLeave, false);
  fra.addEventListener('drop', handleDrop, false);
});