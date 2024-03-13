import pandas as pd
import matplotlib.pyplot as plt
import json


def main():
    month_avg = {"January": ["NA", 0.0], "February": ["NA", 0.0], "March": ["NA", 0.0]}
    for i in range(4):
        try:
            with open(f"files/partition-{i}.json", "r") as json_file:
                stat_dict = json.load(json_file)
                if len(stat_dict) == 2:
                    continue
                # months = list(month_avg.keys())
                for month in month_avg:
                    if month not in stat_dict.keys():
                        continue
                    years = list(stat_dict[month].keys())
                    years = [int(y) for y in years]
                    lastest_year = max(years)
                    if month_avg[month][0] == "NA" or lastest_year > int(month_avg[month][0]):
                        month_avg[month] = [lastest_year, stat_dict[month][str(lastest_year)]["avg"]]
        except FileNotFoundError as e1:
            pass
        except Exception as e2:
            print(str(r))
    
    plot_data = {}
    for month in month_avg:
        plot_data[month + "-" + str(month_avg[month][0])] = month_avg[month][1]

    month_series = pd.Series(plot_data)
    fig, ax = plt.subplots()
    month_series.plot.bar(ax=ax)
    ax.set_ylabel('Avg. Max Temperature')
    plt.tight_layout()
    plt.savefig("/files/month.svg")


if __name__ == "__main__":
    main()