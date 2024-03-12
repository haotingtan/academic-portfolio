# project: p5
# submitter: htan47
# partner: none
# hours: 3

import numpy as np
import pandas as pd
import sklearn
import sklearn.linear_model
import sklearn.compose
import sklearn.pipeline
import sklearn.preprocessing
import sklearn.impute
import sklearn.linear_model


class UserPredictor:
    def __init__(self):
        continuous = ["past_purchase_amt", "total_sec", "total_visit"]
        discrete = ["badge", "age_group", "longest_group"]
        imputer_c = sklearn.impute.SimpleImputer(strategy = "median")
        imputer_d = sklearn.impute.SimpleImputer(strategy = "constant", fill_value = "None")
        transformer_c = sklearn.preprocessing.StandardScaler()
        transformer_d = sklearn.preprocessing.OneHotEncoder()
        steps_c = sklearn.pipeline.Pipeline(steps = [("ic", imputer_c), ("tc", transformer_c)])
        steps_d = sklearn.pipeline.Pipeline(steps = [("id", imputer_d), ("td", transformer_d)])
        pre = sklearn.compose.ColumnTransformer(transformers = [("c", steps_c, continuous), ("d", steps_d, discrete)])
        model = sklearn.pipeline.Pipeline(steps = [("pre", pre), ("clf", sklearn.linear_model.LogisticRegression())])
        
        self.model = model
        self.xcols = ["past_purchase_amt", "age_group", "badge", "total_sec", "total_visit", "longest_group"]
        
        
    def data_process(self, users_basic, users_log):
        total_sec = {}
        for idx in range(len(users_log)):
            user_id = users_log.at[idx, "user_id"]
            if users_log.at[idx, "url"].strip() == '/laptop.html':
                if user_id not in total_sec:
                    total_sec[user_id] = [users_log.at[idx, "seconds"]]
                else:
                    total_sec[user_id].append(users_log.at[idx, "seconds"])
        user_id = []
        total_sec_user = []
        total_visit = []
        longest_visit = []
        
        for key in total_sec:
            user_id.append(key)
            total_sec_user.append(sum(total_sec[key]))
            total_visit.append(len(total_sec[key]))
            longest_visit.append(max(total_sec[key]))
            
        user_on_laptop_df = pd.DataFrame({'user_id': user_id,
                                        'total_sec': total_sec_user,
                                        'total_visit': total_visit,
                                        'longest_visit': longest_visit
                                         })

        users_full_info_df = pd.merge(users_basic, user_on_laptop_df, on='user_id', how='left').fillna(0)
        
        def get_age_group(age):
             return age // 10 * 10
        
        def get_sec_group(sec):
            return int(sec) // 10 * 10
        
        def get_longest_group(sec):
            return int(sec) // 10 * 10 
        
        users_full_info_df["age_group"] = users_full_info_df["age"].apply(get_age_group)
        users_full_info_df["sec_group"] = users_full_info_df["total_sec"].apply(get_sec_group)
        users_full_info_df["longest_group"] = users_full_info_df["longest_visit"].apply(get_longest_group)
        
        return users_full_info_df

    
    def fit(self, train_users, train_logs, train_y):
        users_full_info_df = self.data_process(train_users, train_logs)
        self.model.fit(users_full_info_df[self.xcols], train_y["y"])
    
    
    def predict(self, test_users, test_logs):
        users_full_info_df = self.data_process(test_users, test_logs)
        return self.model.predict(users_full_info_df[self.xcols])
    
    
    