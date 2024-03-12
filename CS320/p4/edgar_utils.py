# project: p4
# submitter: htan47
# partner: none
# hours: 10

import re
import netaddr
from bisect import bisect
import pandas as pd

ips = pd.read_csv("ip2location.csv")


def lookup_region(ip_addr):
    ip = re.sub("[a-zA-Z]", "0", ip_addr)
    ip = int(netaddr.IPAddress(ip))
    idx = bisect(ips["low"], ip)
    return ips.iloc[idx-1]["region"]


class Filing:
    def __init__(self, html):
        dates_raw = re.findall(r"(19\d{2}|20\d{2})-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])", html)
        dates = []
        for date in dates_raw:
            dates.append(date[0] + "-" + date[1] + "-" + date[2])
        self.dates = dates
        
        sic = re.findall(r"SIC=(\d+)", html)
        if sic != None and len(sic) >= 1:
            self.sic = int(sic[0])
        else:
            self.sic = None
        
        addrs = []
        for addr_html in re.findall(r'<div class="mailer">([\s\S]+?)</div>', html):
            lines = []
            for line in re.findall(r'<span class="mailerAddress">([\s\S]+?)</span>', addr_html):
                lines.append(line.strip())
            if len(lines) != 0:
                addrs.append("\n".join(lines))
        self.addresses = addrs

    def state(self):
        for addr in self.addresses:
            results = re.findall(r"[\s\n]([A-Z]{2})\s[0-9]{5}", addr)
            if len(results) >= 1:
                return results[0]