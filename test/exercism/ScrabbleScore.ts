const scrabblePoints: Record<string, number> = Object.freeze({
  a: 1, e: 1, i: 1, o: 1, u: 1, l: 1, n: 1, r: 1, s: 1, t: 1,
  d: 2, g: 2,
  b: 3, c: 3, m: 3, p: 3,
  f: 4, h: 4, v: 4, w: 4, y: 4,
  k: 5,
  j: 8, x: 8,
  q: 10, z: 10
})

function score(word?: string | null): number {
  const letterScore = (c: string) => scrabblePoints[c.toLowerCase()] ?? 0

  // @ts-ignore - false positive - works in https://www.typescriptlang.org/play
  return [...word ?? []].reduce((s, c) => s + letterScore(c), 0)
}

// unit tests
[
  { input: null, expected: 0 },
  { input: undefined, expected: 0 },
  { input: '', expected: 0 },
  { input: 'a', expected: 1 },
  { input: 'A', expected: 1 },
  { input: 'z', expected: 10 },
  { input: 'Zoo', expected: 12 },
  { input: 'street', expected: 6 },
  { input: 'abcdefghijklmnopqrstuvwxyz', expected: 87 },
].map(
  t => ({ ...t, actual: score(t.input) })
).forEach(
  t => console.log(
    `score(${t.input}) -> ${t.actual}`,
    t.actual === t.expected ? '✔' : `❌ expected: ${t.expected}`
  )
)