$(function() {
  $('#slides').slidesjs({
	generateNextPrev:false,
	autoHeight:true,
    play: {
      active: true,
      auto: true,
	  swap: false,
      interval: 3000
    }
  });
});