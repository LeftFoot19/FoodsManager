# -*- coding: utf-8 -*-

import datetime     #日付フォーマット用
import encodings    #jsonエンコーダー用
import json         #json読み込み用
import os           #ファイル取得用

def loadFoodData(path):

    #未フォーマットチェック
    rawFoodDicts = loadsJsonAsDict(path)

    #スプリット("product"キーのvalueを取得)
    rawFoodDictsSplit = splitFoodDicts(rawFoodDicts)

    #フォーマット確認
    foodDicts = [foodDict for foodDict in rawFoodDictsSplit if isFoodDict(foodDict)]

    #Id割当
    foodDictsWithId = allocateId(foodDicts)

    return foodDictsWithId
    
#指定ディレクトリからjsonファイルを辞書形式で取得
def loadsJsonAsDict(path):
    #ファイル一覧取得
    allFileNames = []
    try:
        allFileNames = os.listdir(path)
    except:
        print("Directory is not found at " + path)

    #jsonファイルのみにフィルタ
    jsonFilePathes = [path + "/" + filename for filename in allFileNames if filename.split(".")[-1].lower() == "json"]

    #辞書型に変換
    jsonDicts = []
    for jsonFilePath in jsonFilePathes:
        with open(jsonFilePath, "r") as jsonFile:
            jsonDicts += [json.loads(jsonFile.read(), "utf-8")]
    
    return jsonDicts

#ファイルごと->データごとに分割
def splitFoodDicts(dicts):
    #キー確認
    dataByFiles = [foodDictUnion["product"] for foodDictUnion in dicts if "product" in foodDictUnion]
    #分割
    dataDicts = []
    for dataByFile in dataByFiles:
        dataDicts += dataByFile
    return dataDicts

#フォーマット確認
def isFoodDict(foodDict):

    #バッファ
    isMathch = True

    #iniPrice
    if "iniPrice" in foodDict:
        isMathch = isMathch and foodDict["iniPrice"].isdecimal()
        foodDict["iniPrice"] = foodDict["iniPrice"]
    else:
        isMathch = isMathch and False

    #productName
    isMathch = isMathch and "productName" in foodDict

    #bestBeforeDate
    if "bestBeforeDate" in foodDict:
        try:    #ゴリ押し気味    
            datetime.datetime.strptime(foodDict["bestBeforeDate"], "%Y/%m/%d %H:%M:%S")
        except:
            isMathch = isMathch and False
    else:
        isMathch = isMathch and False

    return isMathch

#Id割当
def allocateId(foodDicts):
    idAllocated = []
    for id, foodDicts in enumerate(foodDicts, 1):
        foodDicts["id"] = str(id)
        idAllocated += [foodDicts]
    return idAllocated
