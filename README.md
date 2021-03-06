# 谁是懂球帝吃鸡模式球员筛选工具

## 一、命令

### 说明

1. 球员位置分PG、SG、SF、PF、C

2. 球员级别分red,yellow,pink,blue,green

3. 球员状态分inpool（在球员池中）、inteam(在球队中)

4. 所有需要传列表的字段，用逗号分隔；如果只传一个则无所谓

### 1. levelUp

**参数说明**

| 参数名称 | 含义                                                       | 是否必填 |
| -------- | ---------------------------------------------------------- | -------- |
| --id     | 升级球员id                                                 | 是       |
| --pos    | 想要升级的目标位置，每个位置间用逗号分隔。不填代表全部位置 | 否       |

**可能出现的错误**

1、**球员不在队伍中**

2、**球员无法升级**（红色）

```
示例：levelUp --id 21 --pos SG,PG,SF
将21号球员升级，目标位置为SG,PG,SF
```



## 2. list

参数说明

| 参数名称 | 含义                                                       | 是否必填 |
| -------- | ---------------------------------------------------------- | -------- |
| --level  | 升级球员id                                                 | 否       |
| --pos    | 想要升级的目标位置，每个位置间用逗号分隔。不填代表全部位置 | 否       |
| --status | 球员状态                                                   | 否       |
| --name   | 球员名称，模糊查询                                         | 否       |

```
示例 list --level red --pos SF --status inpool --name 詹姆斯
列出颜色为red的所有位置为SF且状态为inpool的球员
```



## 3. saveAll

保存所有数据

```
saveAll
```



## 4. randomPick

| 参数名称 | 含义                                                       | 是否必填 |
| -------- | ---------------------------------------------------------- | -------- |
| --level  | 球员级别                                                   | 是       |
| --pos    | 想要升级的目标位置，每个位置间用逗号分隔。不填代表全部位置 | 否       |

示例

```
randomPick --level blue --pos SF,PG,SG
随机选出级别为blue，位置为SF,PG,SG球员
```



## 5.deposit

| 参数名称 | 含义   | 是否必填 |
| -------- | ------ | -------- |
| --id     | 球员id | 是       |

可能的错误

**球员已经在球员池中**

示例

```
deposit 90
向球员池中放入id为90的球员
```



### 6.withdraw

| 参数名称 | 含义   | 是否必填 |
| -------- | ------ | -------- |
| --id     | 球员id | 是       |

可能的错误

**球员已经在队伍中**

示例

```
withdraw 90
从球员池中取出id为90的球员
```



## 7.get

| 参数名称 | 含义   | 是否必填 |
| -------- | ------ | -------- |
| --id     | 球员id | 是       |

如果输入id不存在，会返回空，不会报错

示例

```
get 90
获取id为90的球员信息
```



## 8.export

| 参数名称 | 含义                                                       | 是否必填 |
| -------- | ---------------------------------------------------------- | -------- |
| --level  | 球员级别                                                   | 否       |
| --pos    | 想要升级的目标位置，每个位置间用逗号分隔。不填代表全部位置 | 否       |
| --status | 球员状态                                                   | 否       |

### ***这个建议都不填，因为填的我没测试过***

```
export
导出所有球员
```

