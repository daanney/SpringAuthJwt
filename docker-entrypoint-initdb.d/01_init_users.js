printjson({usernames: "max, peter and john", passwords: "admin"})

let res = [
  db.users.drop(),
  db.users.insert({ firstName: "Max", lastName: "Muster", username: "max", password: "$2y$10$LvoLX/iW3EM5/0Nna6sUh.XLGWg7Q1BGibZ2Jmstu8Nk.G4U4fUy2"}),
  db.users.insert({ firstName: "Peter", lastName: "Example", username: "peter", password: "$2y$10$LvoLX/iW3EM5/0Nna6sUh.XLGWg7Q1BGibZ2Jmstu8Nk.G4U4fUy2"}),
  db.users.insert({ firstName: "John", lastName: "Doe", username: "john", password: "$2y$10$LvoLX/iW3EM5/0Nna6sUh.XLGWg7Q1BGibZ2Jmstu8Nk.G4U4fUy2"})
]

printjson(res)

print('database initialized!')