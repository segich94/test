Принимаються запросы:

POST api/v1/wallet
{
walletId: UUID,
operationType: DEPOSIT or WITHDRAW,
amount: 1000
}

GET api/v1/wallets/{WALLET_UUID}


Запуск с docker-compose
