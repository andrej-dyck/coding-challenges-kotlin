// methods to functions for functional composition
const chars = s => [...s]
const uppercase = s => s.toUpperCase()

const remove = c => a => a.filter(e => e !== c)
const reversed = a => a.reverse()
const map = f => a => a.map(f)
const join = c => a => a.join(c)

// this is the interesting function
const chunked = n => a =>
  a.length && n ? [a.slice(0, n), ...chunked(n)(a.slice(n))] : []

// for functional composition used from a lib, e.g., https://ramdajs.com/
const pipe = (...fns) => (x) => fns.reduce((v, f) => f(v), x)

// format function via function composition
function format({ key, blockSize }) {
  return pipe(
    chars,
    remove('-'),
    reversed,
    chunked(blockSize),
    map(join('')),
    reversed,
    join('-'),
    uppercase
  )(key)
}

const keys = [
  { key: '5F3Z-2e-9-w', blockSize: 4 },
  { key: '2-5g-3-J', blockSize: 2 }
]

console.log('formatted keys', keys.map(format))
// output: formatted keys [ 'Z3F5-W9E2', '2-G5-J3' ]