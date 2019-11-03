let res = [
  db.users.drop(),
  db.users.insert({ firstName: "Max", lastName: "Muster", password: "secret"}),
  db.users.insert({ firstName: "Peter", lastName: "Example", password: "secret"}),
  db.users.insert({ firstName: "John", lastName: "Doe", password: "secret"})
]

printjson(res)

print('database initialized!')