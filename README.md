Operation Mode CBC

CBC - wiązanie bloków zaszyfrowanych, wykorzystuje xor, przed zaszyfrowaniem blok poddawany jest operacji xor z poprzednim kryptogramem

Wektor inicjalizacyjny dodawany jest ze wzgledu na to, ze pierwszy blok nie ma poprzedzajacego kryptogramu

Dobrze jest jezeli blok inicjalizacyjny jest nieprzewidywalny i niepowtarzalny

Padding uzyty jest do wypelnienia ostatniego bloku tak, aby mial 16 bajtów, Padding uzyty to PKCS7Padding. Dodaje on brakujace bajty z wartosciami odpowiadajacymi liczbie dodanych bajtow