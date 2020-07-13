function getDocHeight() {
  return Math.max(
    document.body.scrollHeight,
    document.documentElement.scrollHeight,
    document.body.offsetHeight,
    document.documentElement.offsetHeight,
    document.body.clientHeight,
    document.documentElement.clientHeight,
  );
}

export function amountScrolled() {
  const winHeight =
    window.innerHeight ||
    (document.documentElement || document.body).clientHeight;
  const docHeight = getDocHeight();
  const scrollTop =
    window.pageYOffset ||
    (document.documentElement || document.body.parentNode || document.body)
      .scrollTop;
  const trackLength = docHeight - winHeight;
  return Math.floor((scrollTop / trackLength) * 100);
}
