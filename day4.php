<?php

$code = "ckczppom";
$i = 0;

while ($i < PHP_INT_MAX) {
  if (!strcmp(substr(md5($code . $i), 0, 5), "00000")) break;
  $i++;
}

print("Part 1: $i\n");

// keep searching for "000000", which could have been the answer to part 1 as well
$i--;
while ($i < PHP_INT_MAX) {
  if (!strcmp(substr(md5($code . $i), 0, 6), "000000")) break;
  $i++;
}

print("Part 2: $i\n");

?>
