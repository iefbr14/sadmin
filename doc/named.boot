E: cache           .                       named.cache

The file /etc/named.boot is symlinked to the real file
{{[[DNS_DIRECTORY]]/named.boot}}. It lists the directory where all of the
DNS files live, the site domain name, and a set of primary domains. 
The localhost and 127.0.0 domains are for the localhost IP address (127.0.0.1)
and never need changing.

The primary (db.[[DNS_DOMAIN]]) is for all of '[[DNS_DOMAIN]]' and only
needs to be changed if new primary sites are added. This file is only
added to in Toronto.

The db.rand.RRR (db.rand.tor) file and db.XXX.XXX.XXX (db.198.235.11) are used
to manage the local ethernet.

H2: What to change: db.198.235.11

When added a new host you first must assign it an IP number. Each network must
contain only one IP number per host. (Hosts may have more than one IP number
when they are connected to more than one network).  The db.XXX.XXX.XXX file
can be used to track the assigned IP numbers.  It is also used to map IP
numbers back to names and is used by daemons for validation.

Note that the ';' is used as the comment character.  When adding a new
machine, the last component of the IP number is in the left column. The
'IN PTR' is literal text, and the 'machine.RRR.rand.com.' is the full
machine name. Notice the trailing period on the machine name, this is
required.  In DNS files, any names that don't end with a period, have
the domain appended.  The comes from the domain field on the primary line
as listed in the /etc/named.boot file.  Lastly notice the 'Serial number'.
This is used by DNS servers to track what files have been changed.
Whenever a change is made to a 'db' file, you must increment the serial
number.


E: @       IN SOA  tor.rand.com. root.tor.rand.com. (
E:                 10      ; Serial number
E:                 10800   ; Refresh after 3 hours
E:                 3600    ; Retry after 1 hour
E:                 604800  ; Expire after 1 week
E:                 86400 ) ; Minimum TTL of 1 day
E: 
E: 
E: 201     IN PTR  blue.tor.rand.com.
E: 202     IN PTR  corona.tor.rand.com.


H2: What to change: db.rand.tor

A new entry looks like this:

E: blue            IN A      198.235.11.201
E:                 IN HINFO  "i486/ambra" "WFW 3.11"
E:                 IN TXT    "Barb Nash/Hotline"


The machine name is in the left column. The 'IN A' record maps the name to
an IP address. The 'IN HINFO' is optional and contains the machine-type
and OS-name as a pair of quoted strings. The 'IN TXT' record is free form
text and we use it to track the Owner of the machine.  If it is a PC that
is the person who is the primary user, or for a multi-user box. it is the
Administrator to contact.  Again don't forget to increment the serial number
after making a change.

H2:	What to change: db.rand

In Rand Toronto there is also have entries pointing to the remote sites.
The 'IN NS' record tells other DNS sites that for information on this
sub-domain, look to the following site. The 'IN MX' records list which
sites are willing to hold mail for the named site. The numbers are used
to order the attempts to deliver mail. If a IP-vendor is willing to hold
mail for a site you can add his site between the 'MX 10 local.rand.com.'
and the 'MX 50 tor.rand.com.' records.

E: ; Rochester, New York
E: roc.rand.com.   IN  A       199.98.83.1
E:                 IN  NS      roc.rand.com.
E:                 IN  MX 10   roc.rand.com.
E:                 IN  MX 50   tor.rand.com.
E:                 IN  MX 90   uunorth.north.net.
E:                 IN  HINFO   "sparc-1+" "SunOS 5.3"
E:                 IN  TXT     "John McCall +1-716-383-0160"

Lastly you need to tell the DNS server (named) that these files have
changed. The normal command is 'kill -1 `cat /etc/named.pid`', but I find
this too hard to type and remember. There is a makefile that contains useful
canned commands and one is 'reload'. A 'make reload' causes the DNS server
to reload the database. It is a good idea to check /var/adm/messages
for any errors.

E: % make reload
E:      : did you remember to update the serial number?
E:      kill -1 `cat /etc/named.pid`
E:      tail /var/adm/messages

Also do an 'nslookup' on both the machine and it's IP number to verify
all is well.

E: % nslookup blue
E: Server:  ice.tor.rand.com
E: Address:  198.235.11.87
E: 
E: Name:    blue.tor.rand.com
E: Address:  198.235.11.201


E: % nslookup 198.235.11.201
E: Server:  ice.tor.rand.com
E: Address:  198.235.11.87

E: Name:    blue.tor.rand.com
E: Address:  198.235.11.201

